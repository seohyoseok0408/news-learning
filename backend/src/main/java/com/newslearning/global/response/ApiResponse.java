package com.newslearning.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;  // 응답 메시지
    private T data;          // 응답 데이터

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(null, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("성공", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(message, null);
    }
}
