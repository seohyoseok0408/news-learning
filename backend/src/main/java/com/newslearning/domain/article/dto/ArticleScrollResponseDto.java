package com.newslearning.domain.article.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleScrollResponseDto(
    List<ArticleResponseDto> articles,
    boolean hasNext,
    LocalDateTime nextCursorPublishedAt
) {}
