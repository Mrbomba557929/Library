package com.example.library.mapper;

import com.example.library.domain.model.Url;
import com.example.library.domain.dto.UrlDto;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public UrlDto toDto(Url url) {
        return UrlDto.builder()
                .url(url.getUrl())
                .build();
    }

    public Url toEntity(UrlDto urlDto) {
        return Url.builder()
                .url(urlDto.url())
                .build();
    }
}
