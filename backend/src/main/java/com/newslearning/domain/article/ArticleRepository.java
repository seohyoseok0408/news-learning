package com.newslearning.domain.article;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.newslearning.domain.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 커서 없이 첫 페이지를 조회할 때 사용
    Slice<Article> findAllByOrderByPublishedAtDesc(Pageable pageable);

    // 커서가 있는 경우 해당 시각 이전 기사부터 조회
    Slice<Article> findByPublishedAtLessThanOrderByPublishedAtDesc(LocalDateTime publishedAt, Pageable pageable);

    // 특정 카테고리 내 기사들을 커서 없이 처음부터 조회할 때 사용.
    Slice<Article> findByCategoryOrderByPublishedAtDesc(Article.Category category, Pageable pageable);

    // 특정 카테고리에서 커서를 기준으로 이전 기사들을 조회.
    Slice<Article> findByCategoryAndPublishedAtLessThanOrderByPublishedAtDesc(Article.Category category, LocalDateTime publishedAt, Pageable pageable);

    // 검색어로 기사 제목을 포함하는 기사들을 커서 없이 처음부터 조회.
    Slice<Article> findByTitleContainingIgnoreCaseOrderByPublishedAtDesc(String keyword, Pageable pageable);

    // 검색어로 기사 제목을 포함하고, 커서를 기준으로 이전 기사들을 조회.
    Slice<Article> findByTitleContainingIgnoreCaseAndPublishedAtLessThanOrderByPublishedAtDesc(String keyword, LocalDateTime publishedAt, Pageable pageable);

}
