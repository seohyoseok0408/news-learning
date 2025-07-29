package com.newslearning.domain.article;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newslearning.domain.article.dto.ArticleResponseDto;
import com.newslearning.domain.article.dto.ArticleScrollResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    
    // 커서 기반 무한 스크롤을 위한 기사 목록 조회
    @Transactional(readOnly = true)
    public ArticleScrollResponseDto getArticles(String keyword, String category, LocalDateTime cursorPublishedAt, int size) {
        Pageable pageable = PageRequest.of(0, size);

        Slice<Article> slice;

        // 검색어 있는 경우
        if (keyword != null && !keyword.isBlank()) {
            slice = (cursorPublishedAt == null)
                ? articleRepository.findByTitleContainingIgnoreCaseOrderByPublishedAtDesc(keyword, pageable)
                : articleRepository.findByTitleContainingIgnoreCaseAndPublishedAtLessThanOrderByPublishedAtDesc(keyword, cursorPublishedAt, pageable);

        // 카테고리 조건이 있는 경우
        } else if (category != null) {
            Article.Category cat = Article.Category.valueOf(category.toUpperCase());
            slice = (cursorPublishedAt == null)
                ? articleRepository.findByCategoryOrderByPublishedAtDesc(cat, pageable)
                : articleRepository.findByCategoryAndPublishedAtLessThanOrderByPublishedAtDesc(cat, cursorPublishedAt, pageable);

        // 기본 전체 조회
        } else {
            slice = (cursorPublishedAt == null)
                ? articleRepository.findAllByOrderByPublishedAtDesc(pageable)
                : articleRepository.findByPublishedAtLessThanOrderByPublishedAtDesc(cursorPublishedAt, pageable);

        }
        
        List<ArticleResponseDto> dtoList = slice.getContent().stream()
            .map(ArticleResponseDto::from)
            .toList();

        // 다음 페이지 요청 시 기준이 되는 커서 값
        LocalDateTime nextCursorPublishedAt = slice.hasNext()
            ? dtoList.get(dtoList.size() - 1).publishedAt()
            : null;

        return new ArticleScrollResponseDto(dtoList, slice.hasNext(), nextCursorPublishedAt);
    }
}
