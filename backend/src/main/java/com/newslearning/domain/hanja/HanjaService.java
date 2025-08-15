package com.newslearning.domain.hanja;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newslearning.domain.article.entity.SummaryVersion;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HanjaService {

    private final HanjaRepository hanjaRepository;

    // 특정 기사에 대한 한자 목록을 버전별로 그룹화하여 조회
    @Transactional(readOnly = true)
    public Map<SummaryVersion, List<Hanja>> findGroupedByVersion(Long articleId) {
        List<Hanja> all = hanjaRepository.findByArticleId(articleId);

        Map<SummaryVersion, List<Hanja>> grouped = new EnumMap<>(SummaryVersion.class);
        for (Hanja h : all) {
            grouped.computeIfAbsent(h.getVersion(), k -> new ArrayList<>()).add(h);
        }

        for (SummaryVersion v : SummaryVersion.values()) {
            grouped.putIfAbsent(v, List.of());
        }
        return grouped;
    }

    // 특정 기사와 버전에 해당하는 한자 목록 조회 (퀴즈용)
    @Transactional(readOnly = true)
    public List<Hanja> findByArticleAndVersion(Long articleId, SummaryVersion version) {
        return hanjaRepository.findByArticleIdAndVersion(articleId, version);
    }
}
