package com.newslearning.domain.quiz;

import java.time.LocalDateTime;

import com.newslearning.domain.article.entity.Article;
import com.newslearning.domain.hanja.Hanja;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "quizzes",
       indexes = {
           @Index(name = "idx_quiz_hanja", columnList = "hanja_id"),
           @Index(name = "idx_quiz_article", columnList = "article_id")
       })
public class Quiz {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hanja_id", nullable = false)
    private Hanja hanja;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(name = "answer1", length = 255)
    private String answer1;
    @Column(name = "answer2", length = 255)
    private String answer2;
    @Column(name = "answer3", length = 255)
    private String answer3;

    @Column(name = "correct", nullable = false)
    private Integer correct; // 1~3

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
