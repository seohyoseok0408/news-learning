package com.newslearning.domain.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.newslearning.domain.keyword.dto.KeywordRequestDTO;
import com.newslearning.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
public class KeywordController {

    private final KeywordService keywordService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> add(@RequestBody @Validated KeywordRequestDTO req) {
        keywordService.addKeyword(req.userId(), req.keyword());
        return ResponseEntity.ok(ApiResponse.success()); 
    }

    @DeleteMapping("/{keywordId}")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam Long userId, @PathVariable Long keywordId) {
        keywordService.deleteKeyword(userId, keywordId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
