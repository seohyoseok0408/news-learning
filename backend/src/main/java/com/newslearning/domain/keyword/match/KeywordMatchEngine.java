package com.newslearning.domain.keyword.match;

import java.util.Collection;
import java.util.Set;

/**
 * 키워드 매칭 엔진 인터페이스
 * Aho-Corasick 알고리즘을 사용하여 키워드 매칭을 수행합니다.
 */
public interface KeywordMatchEngine {
    // 키워드 매칭 결과를 나타내는 Hit 레코드
    record Hit(Long userId, Long keywordId) {}

    Set<Hit> matchByNouns(Collection<String> nouns);

    // 키워드 변경 시 재빌드 메서드
    void rebuild(); 
}
