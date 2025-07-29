package com.newslearning.domain.article.dto;

import java.time.LocalDateTime;

import com.newslearning.domain.article.Article;

public record ArticleResponseDto(
    Long id,
    String title,
    String imageUrl,
    String category,
    String reporter,
    LocalDateTime publishedAt
) {
    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(
            article.getId(),
            article.getTitle(),
            article.getImageUrl(),
            article.getCategory().getKorean(),
            article.getReporter(),
            article.getPublishedAt()
        );
    }
}
