package com.newslearning.domain.quiz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newslearning.domain.article.entity.SummaryVersion;
import com.newslearning.domain.quiz.dto.QuizItemDTO;
import com.newslearning.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class QuizController {
    
    private final QuizService quizService;

    @Operation(summary = "기사+버전에 대한 퀴즈 목록 조회")
    @GetMapping("/{id}/quizzes")
    public ResponseEntity<ApiResponse<List<QuizItemDTO>>> getQuizzes(
            @PathVariable Long id,
            @RequestParam SummaryVersion version
    ) {
        var quizzes = quizService.findQuizzesByArticleAndVersion(id, version);

        List<QuizItemDTO> items = new ArrayList<>(quizzes.size());
        for (Quiz q : quizzes) {
            items.add(new QuizItemDTO(
                q.getId(),
                q.getHanja().getId(),
                q.getQuestion(),
                List.of(q.getAnswer1(), q.getAnswer2(), q.getAnswer3())
            ));
        }
        return ResponseEntity.ok(ApiResponse.success(items));
    }
}
