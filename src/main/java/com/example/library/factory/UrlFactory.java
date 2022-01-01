package com.example.library.factory;

import com.example.library.domain.model.Url;
import com.example.library.dto.UrlDto;
import org.springframework.stereotype.Component;

@Component
public class UrlFactory {

    public UrlDto toDto(Url url) {
        return UrlDto.builder()
                .url(url.getUrl())
                .build();
    }
}