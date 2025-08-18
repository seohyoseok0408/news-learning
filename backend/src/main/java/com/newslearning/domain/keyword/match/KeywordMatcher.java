package com.newslearning.domain.keyword.match;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.newslearning.domain.keyword.KeywordRepository;

import org.ahocorasick.trie.Trie;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 키워드 매칭 엔진 구현체
 * Aho-Corasick 알고리즘을 사용
 * DB에 저장된 모든 키워드를 가져와서 -> 
 * Aho-Corasick 알고리즘이란 빠른 검색기를 만들어 둠 ->
 * 이후 기사 본문이 들어오면 한 번만 읽으면서 유저 키워드가 있는지 찾아냄
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KeywordMatcher implements KeywordMatchEngine {

    private final KeywordRepository keywordRepository;
    // Aho-Corasick Trie와 인덱스 맵을 AtomicReference로 관리
    private final AtomicReference<Trie> trieRef = new AtomicReference<>();
    // 키워드 정규화된 문자열과 해당 키워드 ID의 매핑
    private final AtomicReference<Map<String, Set<Hit>>> indexRef = new AtomicReference<>();

    // 초기화 메서드: 애플리케이션 시작 시 키워드 매칭 엔진을 재빌드.
    @PostConstruct
    public void init() { rebuild(); }

    /**
     * 키워드 매칭 엔진을 재빌드합니다.
     * 데이터베이스에서 모든 키워드를 조회하여 Aho-Corasick Trie를 구성.
     * 키워드 추가/삭제 시 KeywordService가 rebuild()를 실행해서 최신 상태 유지.
     */
    @Override
    public synchronized void rebuild() {
        var all = keywordRepository.findAll();
        Map<String, Set<Hit>> map = new HashMap<>();
        for (var k : all) {
            map.computeIfAbsent(k.getNormalizedKeyword(), __ -> new HashSet<>())
               .add(new Hit(k.getUserId(), k.getId()));
        }
        // Trie.TrieBuilder builder = Trie.builder().onlyWholeWords();
        Trie.TrieBuilder builder = Trie.builder();
        map.keySet().forEach(builder::addKeyword);
        trieRef.set(builder.build());
        indexRef.set(map);

        // ✅ 로그 추가
        log.info("🔄 KeywordMatcher rebuilt. 총 키워드 개수 = {}", map.size());
        map.forEach((keyword, hits) ->
            log.debug(" - '{}' -> {}", keyword, hits)
        );
    }

    @Override
    public Set<Hit> matchByNouns(Collection<String> nouns) {
        var idx = indexRef.get();

        if (idx == null || nouns.isEmpty()) return Set.of();

        // 전처리: trim + toLowerCase + 빈값 제거 + 중복 제거
        Set<String> normalized = nouns.stream()
            .filter(Objects::nonNull)
            .map(s -> s.trim().toLowerCase())
            .filter(s -> !s.isEmpty())
            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));

        Set<Hit> hits = new HashSet<>();
        for (String n : normalized) {
            var owners = idx.get(n); // 키워드가 "정규화된 문자열" 기준으로 들어있음
            if (owners != null && !owners.isEmpty()) {
                hits.addAll(owners);
                log.debug("✅ noun 매칭: '{}' -> {}", n, owners);
            }
        }
        if (hits.isEmpty()) {
            log.info("ℹ️ noun 기반 매칭 결과 없음. (nouns.size={})", normalized.size());
        } else {
            log.info("🔔 noun 기반 매칭 결과: 총 {}건", hits.size());
        }
        return hits;
    }
}

