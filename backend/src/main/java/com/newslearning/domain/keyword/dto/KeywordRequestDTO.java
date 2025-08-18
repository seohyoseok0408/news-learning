package com.newslearning.domain.keyword.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KeywordRequestDTO(
        @NotNull Long userId,
        @NotBlank @Size(max = 200) String keyword
) {}
