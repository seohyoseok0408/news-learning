package com.newslearning.domain.user;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.newslearning.domain.user.dto.FileResponse;
import com.newslearning.domain.user.dto.OAuth2UserInfo;
import com.newslearning.domain.user.dto.UserDTO;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Value("${file.upload-dir}")
	private String uploadDir;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 회원 검색 후 미존재하면 저장
	public Optional<User> findOrRegisterSocialUser(OAuth2UserInfo oAuth2UserInfo) {
		return userRepository.findByProviderAndProvider_id(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())
				.or(() -> Optional.of(userRepository.save(User.builder().provider(oAuth2UserInfo.getProvider())
						.provider_id(oAuth2UserInfo.getProviderId()).nickname(oAuth2UserInfo.getNickName())
						.email(oAuth2UserInfo.getEmail()).role("ROLE_USER") // 기본 역할 지정 예시
						.score(0).build())));
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
		if (optUser.isPresent()) {
			User user = optUser.get();

			String imageUrl = null;
			if (user.getProfile_img() != null) {
				imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/profile_img/")
						.path(String.valueOf(user.getId())).toUriString();
			}

			UserDTO userdto = UserDTO.builder().provider(user.getProvider()).nickname(user.getNickname())
					.profile_img(imageUrl).email(user.getEmail()).score(user.getScore()).build();

			return userdto;
		} else {
			return null;
		}
	}

	// 프로필 추가
	public void saveProfile_img(MultipartFile file) {

		User user = userRepository.findByProviderId(getId())
				.orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

		try {
			// 저장 디렉토리 (예: C:/news-learning/uploads/profile/{userId})
			Path baseDir = Paths.get(uploadDir, "profile", String.valueOf(user.getId())).toAbsolutePath().normalize();
			Files.createDirectories(baseDir);

			// 원본 파일명 그대로 사용
			String original = file.getOriginalFilename();
			if (original == null) {
				original = "profile"; // 파일명이 null일 경우 기본값
			}

			Path target = baseDir.resolve(original).normalize();

			// 저장 (같은 이름이 있으면 덮어씀)
			try (var in = file.getInputStream()) {
				Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
			}

			// 기존 프로필 파일 삭제 로직은 필요하다면 유지 가능
			if (user.getProfile_img() != null) {
				try {
					Path old = Paths.get(user.getProfile_img());
					if (old.startsWith(Paths.get(uploadDir).toAbsolutePath().normalize())) {
						Files.deleteIfExists(old);
					}
				} catch (Exception ignore) {
					/* 로깅만 해도 됨 */ }
			}

			// DB에는 경로 저장
			user.setProfile_img(target.toString());
			userRepository.save(user);

		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패", e);
		}
	}

	// 프로필 사진 받기
	public FileResponse loadProfileImage(int userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

	    String path = user.getProfile_img();
	    if (path == null) {
	        throw new IllegalStateException("프로필 이미지가 없습니다.");
	    }

	    try {
	        Path filePath = Paths.get(path).toAbsolutePath().normalize();
	        Resource resource = new UrlResource(filePath.toUri());
	        if (!resource.exists() || !resource.isReadable()) {
	            throw new RuntimeException("파일을 읽을 수 없습니다.");
	        }

	        String ct = Files.probeContentType(filePath);
	        MediaType mediaType = (ct != null) ? MediaType.parseMediaType(ct) : MediaType.APPLICATION_OCTET_STREAM;

	        return new FileResponse(resource, mediaType);

	    } catch (Exception e) {
	        throw new RuntimeException("파일 로드 실패", e);
	    }
	}

}
