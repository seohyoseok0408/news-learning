package com.newslearning.domain.hanja;

import java.time.LocalDateTime;

import com.newslearning.domain.article.entity.Article;
import com.newslearning.domain.article.entity.SummaryVersion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "hanja",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_article_version_word",
                             columnNames = {"article_id", "version", "word"})
       },
       indexes = {
           @Index(name = "idx_hanja_article_version", columnList = "article_id, version")
       })
public class Hanja {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Enumerated(EnumType.STRING)
    @Column(name = "version", nullable = false)
    private SummaryVersion version;

    @Column(nullable = false, length = 100)
    private String word;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String definition;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
