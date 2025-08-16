package com.newslearning.domain.article.dto;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.newslearning.domain.article.entity.Article;
import com.newslearning.domain.article.entity.SummaryVersion;
import com.newslearning.domain.hanja.Hanja;
import com.newslearning.domain.hanja.dto.HanjaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class ArticleDetailResponseDTO {
    private Long articleId;
    private String title;
    private String reporter;
    private String mediaName;
    private String imageUrl;
    private Map<SummaryVersion, SummaryBlock> summaries;
    private List<ArticleTeaserDTO> more; 

    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class SummaryBlock {
        private String content;
        private List<HanjaDTO> hanjaWords; 
    }

    public record ArticleTeaserDTO(Long id, String title) {
        public static ArticleTeaserDTO from(Article a) {
            return new ArticleTeaserDTO(a.getId(), a.getTitle());
        }
    }

    public static ArticleDetailResponseDTO of(
        Article article,
        Map<SummaryVersion, List<Hanja>> hanjaByVersion,
        List<ArticleTeaserDTO> more 
    ) {
        Map<SummaryVersion, SummaryBlock> map = new EnumMap<>(SummaryVersion.class);

        map.put(SummaryVersion.ORIGINAL, new SummaryBlock(
            article.getContent(),
            hanjaByVersion.getOrDefault(SummaryVersion.ORIGINAL, List.of()).stream().map(HanjaDTO::from).toList()
        ));
        map.put(SummaryVersion.SHORT, new SummaryBlock(
            article.getShortSummary(),
            hanjaByVersion.getOrDefault(SummaryVersion.SHORT, List.of()).stream().map(HanjaDTO::from).toList()
        ));
        map.put(SummaryVersion.MIDDLE, new SummaryBlock(
            article.getMiddleSummary(),
            hanjaByVersion.getOrDefault(SummaryVersion.MIDDLE, List.of()).stream().map(HanjaDTO::from).toList()
        ));

        return new ArticleDetailResponseDTO(
            article.getId(),
            article.getTitle(),
            article.getReporter(),
            article.getMediaName(),
            article.getImageUrl(),
            map,
            more == null ? List.of() : more
        );
    }
}
