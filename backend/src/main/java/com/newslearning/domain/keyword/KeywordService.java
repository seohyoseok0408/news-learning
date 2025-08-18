package com.newslearning.domain.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newslearning.domain.keyword.match.KeywordMatchEngine;

import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordMatchEngine matchEngine;

    // 키워드 정규화: 공백 제거, 소문자 변환, NFKC 정규화
    private String norm(String s) {
        if (s == null) return "";
        String out = s.trim().toLowerCase();
        out = Normalizer.normalize(out, Normalizer.Form.NFKC);
        return out;
    }

    @Transactional
    public void addKeyword(Long userId, String keyword) {
        String normalized = norm(keyword);
        if (normalized.isEmpty()) throw new IllegalArgumentException("빈 키워드");

        // 이미 있으면 아무 것도 하지 않음
        if (keywordRepository.existsByUserIdAndNormalizedKeyword(userId, normalized)) {
            return;
        }

        keywordRepository.save(Keyword.builder()
                .userId(userId)
                .keyword(keyword)
                .normalizedKeyword(normalized)
                .build());

        matchEngine.rebuild();
    }

    @Transactional
    public void deleteKeyword(Long userId, Long keywordId) {
        var kw = keywordRepository.findById(keywordId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 키워드 ID입니다. Id: " + keywordId));

        // 본인 키워드만 삭제 가능
        if (!kw.getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인 키워드만 삭제 가능합니다.");
        }

        keywordRepository.delete(kw);
        matchEngine.rebuild();
    }
}
