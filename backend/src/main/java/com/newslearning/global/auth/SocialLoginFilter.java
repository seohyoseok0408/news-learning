package com.newslearning.global.auth;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.newslearning.domain.user.CustomUserDetails;
import com.newslearning.domain.user.User;
import com.newslearning.domain.user.UserRepository;
import com.newslearning.domain.user.UserService;
import com.newslearning.domain.user.dto.GoogleUserInfo;
import com.newslearning.domain.user.dto.NaverUserInfo;
import com.newslearning.domain.user.dto.OAuth2UserInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpMethod;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class SocialLoginFilter extends OncePerRequestFilter {

	// 프론트(소셜 로그인은 네이버와 구글만 가능하도록 했음(수정 가능))
	// POST요청, Social 헤더로 토큰 넣기
	// provider 키로 플랫폼 넣기(naver, google)

	/*
	 * 토큰 유효 검사 및 실패처리
	 * 가져온 정보로 DB 조회해서 존재하면 로그인 성공 존재하지 않으면 회원가입
	 * JWT 발급하는 로직 추가
	 */

	private final UserService userService;
	private final JWTUtil jwtUtil;

	public SocialLoginFilter(UserService userService, JWTUtil jwtUtil) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 소셜 로그인 요청 경로가 아니면 그냥 넘김
		if (!request.getRequestURI().startsWith("/social-login") || !request.getMethod().equals("POST")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Social 헤더에서 토큰 추출
		// 프론트는 Social을 키로 토큰 담아서 줘야함.
		String authHeader = request.getHeader("Social");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			// Social 헤더 잘못 넣었을 때
			// 오류처리 필요
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("유효하지 않은 Social access 토큰");
			return;
		}

		// "Bearer " 제거
		String socialAccessToken = authHeader.substring(7);

		// 플랫폼 확인
		String provider = request.getParameter("provider");

		// 사용자의 요청에서 플랫폼 확인 및 사용자 정보 받아올 경로
		String userInfoUrl = provider;

		if (provider.equals("google")) {
			userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
		} else if(provider.equals("naver")) {
			userInfoUrl = "https://openapi.naver.com/v1/nid/me";
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("지원하지 않는 소셜 로그인");
			
			// 지원하지 않는 플랫폼
			// 예외처리 필요
		}
		
		// 소셜 플랫폼에서 사용자 정보 가져오기
		try {
			// 프론트에게 받은 토큰으로 플랫폼에 사용자 정보 요청
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + socialAccessToken);
			HttpEntity<Void> entity = new HttpEntity<>(headers);

			ResponseEntity<Map> responseEntity = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);

			Map<String, Object> userAttributes = responseEntity.getBody();
			System.out.println(" 소셜 로그인 정보:" + userAttributes);

			// 플랫폼별 DTO에 맞게 넣기
			OAuth2UserInfo oAuth2UserInfo = switch (provider) {
			case "google" -> new GoogleUserInfo(userAttributes);
			case "naver" -> new NaverUserInfo(userAttributes);
			default -> throw new IllegalArgumentException("Unknown provider");
			};

			// DB에 사용자 등록 or 조회
			Optional<User> userOpt = userService.findOrRegisterSocialUser(oAuth2UserInfo);
			User user = userOpt.get();
			
			// JWT 생성 준비
			CustomUserDetails customUserDetails = new CustomUserDetails(user);
			UsernamePasswordAuthenticationToken authentication =
				    new UsernamePasswordAuthenticationToken(
				        customUserDetails,
				        null,
				        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
				    );
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			// JWT 생성
			String username = customUserDetails.getUsername();
			String role = "ROLE_" + user.getRole(); // 권한 접두사 붙이기
			String access = jwtUtil.createJwt("access", username, role, 60 * 60 * 1000 * 10L); // 10시간 유효
			String refresh = jwtUtil.createJwt("refresh", username, role, 60 * 60 * 1000 * 24L); // 24시간
			
			// JWT 응답
			response.addHeader("access", "Bearer " + access);
			response.addCookie(createCookie("refresh", refresh));

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("소셜 인증 실패: " + e.getMessage());
		}
	}
	
	// 쿠키 생성을 위한 메서드
	private Cookie createCookie(String key, String value) {
	    Cookie cookie = new Cookie(key, value);
	    cookie.setMaxAge(24*60*60);
	    cookie.setHttpOnly(true);
	    return cookie;
	}
}
