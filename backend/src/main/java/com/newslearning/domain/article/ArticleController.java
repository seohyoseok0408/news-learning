package com.newslearning.domain.article;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newslearning.domain.article.dto.ArticleScrollResponseDto;
import com.newslearning.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "기사 목록 조회", description = "publishedAt 기준 내림차순 커서 기반 무한스크롤용 기사 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ArticleScrollResponseDto>> getArticles(
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime cursorPublishedAt,
        @RequestParam(defaultValue = "10") int size) {
        ArticleScrollResponseDto response = articleService.getArticles(cursorPublishedAt, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

