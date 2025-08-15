package com.newslearning.domain.article;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newslearning.domain.article.dto.ArticleDetailResponseDTO;
import com.newslearning.domain.article.dto.ArticleRequestDTO;
import com.newslearning.domain.article.dto.ArticleScrollResponseDTO;
import com.newslearning.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Article", description = "뉴스 기사 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "기사 목록 조회", description = "카테고리, 검색어, 커서를 기반으로 기사 목록을 무한스크롤 방식으로 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ArticleScrollResponseDTO>> getArticles(
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime cursorPublishedAt,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String category, 
        @RequestParam(required = false) String keyword) {
        ArticleScrollResponseDTO response = articleService.getArticles(keyword, category, cursorPublishedAt, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "단일 기사 조회", description = "기사 ID를 기반으로 기사의 상세 정보를 조회합니다.(한자어 포함, 퀴즈 제외)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleDetailResponseDTO>> getArticleById(@PathVariable Long id) {
        ArticleDetailResponseDTO response = articleService.getArticleDetail(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "기사 + 한자 + 퀴즈 일괄 등록", description = "기사, 한자, 퀴즈 정보를 포함한 DTO를 기반으로 일괄 등록합니다. ")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addArticle(
            @Valid @RequestBody ArticleRequestDTO request) {
        articleService.addArticle(request);
        return ResponseEntity.ok(ApiResponse.success());
    }
}

