package com.newslearning.domain.user;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.newslearning.domain.user.dto.OAuth2UserInfo;
import com.newslearning.domain.user.dto.UserDTO;
import com.newslearning.global.response.ApiResponse;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 회원 검색 후 미존재하면 저장
	public Optional<User> findOrRegisterSocialUser(OAuth2UserInfo oAuth2UserInfo) {
	    return userRepository.findByProviderAndProvider_id(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())
	            .or(() -> Optional.of(userRepository.save(
	                    User.builder()
	                        .provider(oAuth2UserInfo.getProvider())
	                        .provider_id(oAuth2UserInfo.getProviderId())
	                        .nickname(oAuth2UserInfo.getNickName())
	                        .email(oAuth2UserInfo.getEmail())
	                        .role("ROLE_USER") // 기본 역할 지정 예시
	                        .score(0)
	                        .build()
	            )));
	}
	
	// 현재 사용자 아이디
	public String getId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	// 현재 사용자 권한
	public String getRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		return role;
	}

	// 내정보 확인
	public UserDTO myInfo() {
		Optional<User> optUser = userRepository.findByProviderId(getId());
		if(optUser.isPresent()) {
			User user = optUser.get();
			
			UserDTO userdto = UserDTO.builder().provider(user.getProvider())
			.nickname(user.getNickname())
			.profile_img(user.getProfile_img())
			.email(user.getEmail())
			.score(user.getScore())
			.build();
			
			return userdto;
		} else {
			return null;
		}
	}

}
