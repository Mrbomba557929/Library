package com.example.library.factory;

import com.example.library.domain.model.Url;
import com.example.library.domain.dto.UrlDto;
import org.springframework.stereotype.Component;

@Component
public class UrlFactory {

    public UrlDto toDto(Url url) {
        return UrlDto.builder()
                .url(url.getUrl())
                .build();
    }

    public Url toEntity(UrlDto urlDto) {
        return Url.builder()
                .url(urlDto.getUrl())
                .build();
    }
}
