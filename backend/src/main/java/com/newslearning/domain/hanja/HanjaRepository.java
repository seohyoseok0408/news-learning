package com.newslearning.domain.hanja;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newslearning.domain.article.entity.SummaryVersion;

public interface HanjaRepository extends JpaRepository<Hanja, Long> {
    // 특정 기사에 대한 한자 목록을 조회
    List<Hanja> findByArticleId(Long articleId);
    
    // 특정 기사와 버전에 대한 한자 목록을 조회
    List<Hanja> findByArticleIdAndVersion(Long articleId, SummaryVersion version);
}
