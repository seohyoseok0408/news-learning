package com.newslearning.domain.hanja.dto;

import com.newslearning.domain.hanja.Hanja;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class HanjaDTO {
    private Long id;
    private String word;
    private String definition;

    public static HanjaDTO from(Hanja h) {
        return new HanjaDTO(h.getId(), h.getWord(), h.getDefinition());
    }
}
