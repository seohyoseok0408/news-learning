package com.newslearning.domain.article;

import java.time.LocalDateTime;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "short_summary", length = 300)
    private String shortSummary;

    @Column(name = "middle_summary", length = 300)
    private String middleSummary;

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Column(length = 100, nullable = false)
    private String reporter;

    @Column(name = "source_url", length = 500, nullable = false)
    private String sourceUrl;

    @Column(name = "media_name", length = 100, nullable = false)
    private String mediaName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Category {
        POLITICS("정치"),
        ECONOMY("경제"),
        SOCIETY("사회"),
        CULTURE("연예"),
        WORLD("국제"),
        SPORTS("스포츠"),
        ETC("전체"); 

        private final String korean;

        Category(String korean) {
            this.korean = korean;
        }

        public String getKorean() {
            return korean;
        }

        public static Category fromKorean(String korean) {
            return Arrays.stream(Category.values())
                .filter(c -> c.korean.equals(korean))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 카테고리: " + korean));
        }
    }
}
