package com.newslearning.domain.keyword;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "keyword",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "normalized_keyword"})
)
@Getter 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class Keyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아직 User 엔티티 미병합 → 값으로만 보관(추후 ManyToOne으로 대체 가능)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "keyword", nullable = false, length = 200)
    private String keyword;

    @Column(name = "normalized_keyword", nullable = false, length = 200)
    private String normalizedKeyword;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
