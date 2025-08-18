package com.newslearning.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
	
	private String provider;
	
	private String nickname;
	
	private String profile_img;
	
	private String email;
	
	private int score;
}
