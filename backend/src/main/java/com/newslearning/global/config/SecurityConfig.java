package com.newslearning.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.newslearning.global.auth.JWTFilter;
import com.newslearning.global.auth.JWTUtil;
import com.newslearning.global.auth.SocialLoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final SocialLoginFilter socialLoginFilter;
	private final JWTUtil jwtUtil;

    public SecurityConfig(SocialLoginFilter socialLoginFilter, JWTUtil jwtUtil) {
        this.socialLoginFilter = socialLoginFilter;
        this.jwtUtil = jwtUtil;
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// csrf disable
		http.csrf((auth) -> auth.disable())
		// From 로그인 방식 disable
		.formLogin((auth) -> auth.disable())
		// http basic 인증 방식 disable
		.httpBasic((auth) -> auth.disable())
		// 세션 설정
		.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		// 경로별 인가 작업
		.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/social-login", "/reissue").permitAll()
				.requestMatchers("/user").permitAll()
				.anyRequest().permitAll());
		// 소셜로그인 필터 추가(소셜로그인 및 회원가입까지)
		http
		.addFilterBefore(socialLoginFilter,
                UsernamePasswordAuthenticationFilter.class);
		// JWT 검증 필터 추가
		http
        .addFilterBefore(new JWTFilter(jwtUtil), SocialLoginFilter.class);

		return http.build();
	}
}