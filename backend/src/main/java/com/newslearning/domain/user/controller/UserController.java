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
	
	@GetMapping("/social-login")
   public ResponseEntity<String> callback(
           @RequestParam(name = "code") String code,
           @RequestParam(name = "state", required = false) String state,
           @RequestParam(name = "error", required = false) String error,
           @RequestParam(name = "error_description", required = false) String errorDesc) {

       if (error != null) {
           return ResponseEntity.badRequest()
                   .body("naver error=" + error + ", desc=" + errorDesc);
       }
       return ResponseEntity.ok("code=" + code + ", state=" + state);
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
