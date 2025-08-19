package com.newslearning.domain.user.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.newslearning.domain.user.UserService;
import com.newslearning.domain.user.dto.FileResponse;
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
	
	// 프로필 사진 받기
	@GetMapping("/user/profile_img/{userId}")
	public ResponseEntity<Resource> getProfileImg(@PathVariable("userId") int userId) {
	    FileResponse file = userService.loadProfileImage(userId);
	    return ResponseEntity.ok()
	            .contentType(file.contentType())
	            .body(file.resource());
	}
	
	// 프로필 추가
	@PostMapping("/user/profile_img")
    public ResponseEntity<ApiResponse<Void>> upload(@RequestParam("file") MultipartFile file) {
		userService.saveProfile_img(file);
        return ResponseEntity.ok(ApiResponse.success());
    }
	
}
