package com.example.library.service.impl;

import com.example.library.domain.model.Url;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.UrlRepository;
import com.example.library.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

/**
 * Implementation the {@link UrlService} interface.
 */
@RequiredArgsConstructor
@Component
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Override
    public Url save(Url url) {
        try {
            return urlRepository.save(url.getUrl());
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .link("UrlServiceImpl/save")
                    .build(FailedToSaveException.class);
        }
    }
}
