package com.example.library.service;

import com.example.library.domain.model.Url;
import com.example.library.factory.UrlFactory;
import com.example.library.repository.UrlRepository;
import com.example.library.service.impl.UrlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock private UrlRepository urlRepository;
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        urlService = new UrlServiceImpl(urlRepository);
    }

    @DisplayName("Test should properly save the url")
    @Test
    void shouldProperlySaveTheUrl() {
        //given
        Url url = UrlFactory.generator(1).generate().get(0);
        Mockito.when(urlRepository.save(url.getUrl())).thenReturn(url);

        //when
        Url savedUrl = urlService.save(url);

        //then
        verify(urlRepository, Mockito.times(1)).save(url.getUrl());
        assertThat(savedUrl).isEqualTo(url);
    }
}