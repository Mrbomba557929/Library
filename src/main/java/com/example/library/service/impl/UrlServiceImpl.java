package com.example.library.service.impl;

import com.example.library.domain.model.Url;
import com.example.library.repository.UrlRepository;
import com.example.library.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation the {@link UrlService} interface.
 */
@RequiredArgsConstructor
@Component
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Override
    public Url save(Url url) {
        return urlRepository.save(url.getUrl());
    }
}
