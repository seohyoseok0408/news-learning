package com.newslearning.domain.keyword;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    // 사용자 ID로 키워드 목록 조회
    List<Keyword> findByUserId(Long userId);

    // 사용자 ID와 정규화된 키워드로 키워드 조회
    Optional<Keyword> findByUserIdAndNormalizedKeyword(Long userId, String normalized);

    // 사용자 ID와 정규화된 키워드로 존재 여부 확인
    boolean existsByUserIdAndNormalizedKeyword(Long userId, String normalized);
}
