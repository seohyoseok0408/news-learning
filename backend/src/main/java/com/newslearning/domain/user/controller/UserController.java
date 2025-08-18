package com.newslearning.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newslearning.domain.user.User;
import com.newslearning.domain.user.UserService;
import com.newslearning.domain.user.dto.UserDTO;
import com.newslearning.global.response.ApiResponse;

@RestController	
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// 내정보 확인
	@GetMapping("/user/myInfo")
	public ResponseEntity<ApiResponse<UserDTO>> myInfo() {
		UserDTO userdto = userService.myInfo();
		return ResponseEntity.ok(ApiResponse.success(userdto));
	}
	
	// 프로필 추가
	
	// 회원정보 변경
	
}
