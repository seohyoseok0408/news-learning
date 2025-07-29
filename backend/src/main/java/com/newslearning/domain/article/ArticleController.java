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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Article", description = "뉴스 기사 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "기사 목록 조회", description = "카테고리, 검색어, 커서를 기반으로 기사 목록을 무한스크롤 방식으로 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ArticleScrollResponseDto>> getArticles(
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime cursorPublishedAt,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String category, 
        @RequestParam(required = false) String keyword) {
        ArticleScrollResponseDto response = articleService.getArticles(keyword, category, cursorPublishedAt, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

