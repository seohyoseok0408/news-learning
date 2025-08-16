package com.newslearning.domain.article.dto;

import java.time.LocalDateTime;

import com.newslearning.domain.article.entity.Article;

public record ArticleResponseDTO(
    Long id,
    String title,
    String imageUrl,
    String category,
    String reporter,
    LocalDateTime publishedAt
) {
    public static ArticleResponseDTO from(Article article) {
        return new ArticleResponseDTO(
            article.getId(),
            article.getTitle(),
            article.getImageUrl(),
            article.getCategory().getKorean(),
            article.getReporter(),
            article.getPublishedAt()
        );
    }
}
