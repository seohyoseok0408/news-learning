package com.newslearning.domain.article;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 커서 없이 첫 페이지를 조회할 때 사용
    Slice<Article> findAllByOrderByPublishedAtDesc(Pageable pageable);

    // 커서가 있는 경우 해당 시각 이전 기사부터 조회
    Slice<Article> findByPublishedAtLessThanOrderByPublishedAtDesc(LocalDateTime publishedAt, Pageable pageable);
}
