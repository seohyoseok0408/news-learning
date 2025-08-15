// package: com.newslearning.domain.article.dto
package com.newslearning.domain.article.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.newslearning.domain.article.entity.Article.Category;
import com.newslearning.domain.article.entity.SummaryVersion;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ArticleRequestDTO {

    // ===== Article =====
    @NotBlank private String title;
    @NotBlank private String content;
    private String shortSummary;
    private String middleSummary;

    private String imageUrl;
    @NotBlank private String reporter;
    @NotBlank private String sourceUrl;   // 중복 방지 키로 사용 권장
    @NotBlank private String mediaName;

    @NotNull  private Category category;
    @NotNull  private LocalDateTime publishedAt;

    // ===== Hanja + Quizzes =====
    @NotNull @Size(min = 0)
    private List<HanjaBlock> hanjas;  // 버전별 한자와 그 한자에 속한 퀴즈들

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class HanjaBlock {
        @NotNull private SummaryVersion version;  // ORIGINAL | SHORT | MIDDLE
        @NotBlank private String word;
        @NotBlank private String definition;

        @Builder.Default
        private List<QuizBlock> quizzes = List.of(); // 선택
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class QuizBlock {
        @NotBlank private String question;
        @NotBlank private String answer1;
        @NotBlank private String answer2;
        @NotBlank private String answer3;
        @NotNull  @Min(1) @Max(3) private Integer correct; // 1~3
    }
}
