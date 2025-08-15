package com.newslearning.domain.quiz;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // 특정 한자 ID 목록에 해당하는 퀴즈를 조회
    List<Quiz> findByHanjaIdIn(Collection<Long> hanjaIds);
}
