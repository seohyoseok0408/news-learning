package com.newslearning.domain.article;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.newslearning.domain.article.dto.ArticleResponseDto;
import com.newslearning.domain.article.dto.ArticleScrollResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    
    // 커서 기반 무한 스크롤을 위한 기사 목록 조회
    public ArticleScrollResponseDto getArticles(LocalDateTime cursorPublishedAt, int size) {
        Pageable pageable = PageRequest.of(0, size);

        // 커서가 null이면 최신 기사부터 조회, 있으면 해당 시각 이전 기사부터 조회
        Slice<Article> slice = (cursorPublishedAt == null)
            ? articleRepository.findAllByOrderByPublishedAtDesc(pageable)
            : articleRepository.findByPublishedAtLessThanOrderByPublishedAtDesc(cursorPublishedAt, pageable);

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
