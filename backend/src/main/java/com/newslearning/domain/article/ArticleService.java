package com.newslearning.domain.article;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newslearning.domain.article.dto.ArticleDetailResponseDTO;
import com.newslearning.domain.article.dto.ArticleRequestDTO;
import com.newslearning.domain.article.dto.ArticleResponseDTO;
import com.newslearning.domain.article.dto.ArticleScrollResponseDTO;
import com.newslearning.domain.article.entity.Article;
import com.newslearning.domain.hanja.Hanja;
import com.newslearning.domain.hanja.HanjaRepository;
import com.newslearning.domain.hanja.HanjaService;
import com.newslearning.domain.quiz.Quiz;
import com.newslearning.domain.quiz.QuizRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final HanjaService hanjaService;
    private final HanjaRepository hanjaRepository;
    private final QuizRepository quizRepository;

    @Getter
    @AllArgsConstructor
    public static class IngestResult {
        private Long articleId;
        private int hanjaInserted;
        private int quizInserted;
    }
    
    // 커서 기반 무한 스크롤을 위한 기사 목록 조회
    @Transactional(readOnly = true)
    public ArticleScrollResponseDTO getArticles(String keyword, String category, LocalDateTime cursorPublishedAt, int size) {
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
        
        List<ArticleResponseDTO> dtoList = slice.getContent().stream()
            .map(ArticleResponseDTO::from)
            .toList();

        // 다음 페이지 요청 시 기준이 되는 커서 값
        LocalDateTime nextCursorPublishedAt = slice.hasNext()
            ? dtoList.get(dtoList.size() - 1).publishedAt()
            : null;

        return new ArticleScrollResponseDTO(dtoList, slice.hasNext(), nextCursorPublishedAt);
    }

    // 특정 기사 상세 조회
    @Transactional(readOnly = true)
    public ArticleDetailResponseDTO getArticleDetail(Long articleId) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 기사가 존재하지 않습니다. id=" + articleId));

        // 버전별 한자어만 묶어서 응답 (퀴즈는 제외)
        var hanjaByVersion = hanjaService.findGroupedByVersion(articleId);
        
        var teasers = articleRepository
            .findTop3ByCategoryAndIdNotOrderByPublishedAtDesc(article.getCategory(), article.getId())
            .stream().map(ArticleDetailResponseDTO.ArticleTeaserDTO::from).toList();

        return ArticleDetailResponseDTO.of(article, hanjaByVersion, teasers);
    }

    /**
     * 파이썬 → 스프링 한 번의 요청으로
     * 기사 + 한자 + 퀴즈를 INSERT 합니다. (UPSERT X)
     * 유니크 제약 위반 시 DataIntegrityViolationException 발생 가능.
     */
    @Transactional
    public IngestResult addArticle(ArticleRequestDTO req) {
        // 0) 타임스탬프 기본값
        LocalDateTime createdTs = LocalDateTime.now();

        // 1) Article 저장 (단순 insert)
        Article article = Article.builder()
            .title(req.getTitle())
            .content(req.getContent())
            .shortSummary(req.getShortSummary())
            .middleSummary(req.getMiddleSummary())
            .imageUrl(req.getImageUrl())
            .reporter(req.getReporter())
            .sourceUrl(req.getSourceUrl())
            .mediaName(req.getMediaName())
            .category(req.getCategory())
            .publishedAt(req.getPublishedAt())
            .createdAt(LocalDateTime.now())
            .build();
        article = articleRepository.save(article);

        // 2) Hanja 저장 + 3) Quiz 저장 (단순 insert)
        int hanjaCnt = 0;
        int quizCnt  = 0;

        if (req.getHanjas() != null) {
            for (var hb : req.getHanjas()) {
                Hanja hanja = Hanja.builder()
                    .article(article)
                    .version(hb.getVersion())
                    .word(hb.getWord())
                    .definition(hb.getDefinition())
                    .createdAt(createdTs)
                    .build();
                hanja = hanjaRepository.save(hanja);
                hanjaCnt++;

                if (hb.getQuizzes() != null) {
                    for (var qb : hb.getQuizzes()) {
                        Quiz quiz = Quiz.builder()
                            .article(article)
                            .hanja(hanja)
                            .question(qb.getQuestion())
                            .answer1(qb.getAnswer1())
                            .answer2(qb.getAnswer2())
                            .answer3(qb.getAnswer3())
                            .correct(qb.getCorrect())
                            .createdAt(createdTs)
                            .build();
                        quizRepository.save(quiz);
                        quizCnt++;
                    }
                }
            }
        }

        return new IngestResult(article.getId(), hanjaCnt, quizCnt);
    }
}
