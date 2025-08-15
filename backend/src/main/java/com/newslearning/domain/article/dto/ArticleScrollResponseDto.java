package com.newslearning.domain.article.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleScrollResponseDTO(
    List<ArticleResponseDTO> articles,
    boolean hasNext,
    LocalDateTime nextCursorPublishedAt
) {}
