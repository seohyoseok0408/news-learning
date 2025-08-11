package com.newslearning.domain.quiz.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "QuizItem", description = "한자어 퀴즈 항목")
public class QuizItemDTO {
    @Schema(description = "퀴즈 ID")
    private Long quizId;
    @Schema(description = "한자 ID")
    private Long hanjaId;
    @Schema(description = "문항")
    private String question;
    @Schema(description = "보기 3개")
    private List<String> options;
}
