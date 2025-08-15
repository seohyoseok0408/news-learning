package com.newslearning.domain.quiz;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newslearning.domain.article.entity.SummaryVersion;
import com.newslearning.domain.hanja.Hanja;
import com.newslearning.domain.hanja.HanjaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    
    private final QuizRepository quizRepository;
    private final HanjaService hanjaService;

    /**
     * 사용자가 "퀴즈풀기"를 눌렀을 때 호출:
     * - 해당 기사 + 버전에 해당하는 Hanja를 모두 찾고
     * - 그 Hanja들에 걸린 Quiz를 한 번에 가져온다.
     */
    @Transactional(readOnly = true)
    public List<Quiz> findQuizzesByArticleAndVersion(Long articleId, SummaryVersion version) {
        List<Hanja> hanjas = hanjaService.findByArticleAndVersion(articleId, version);
        List<Long> hanjaIds = hanjas.stream().map(Hanja::getId).toList();
        if (hanjaIds.isEmpty()) return List.of();
        List<Quiz> quizzes = quizRepository.findByHanjaIdIn(hanjaIds);
        return quizzes;
    }
}
