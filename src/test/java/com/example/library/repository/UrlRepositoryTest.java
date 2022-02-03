package com.example.library.repository;

import com.example.library.domain.model.Url;
import com.example.library.factory.UrlFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UrlRepositoryTest extends AbstractRepositoryTest {

    private final UrlRepository urlRepository;
    private final UrlFactory urlFactory;

    @Autowired
    public UrlRepositoryTest(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.urlFactory = new UrlFactory();
    }

    @DisplayName("Test should properly save the url")
    @Test
    void shouldProperlySave() {
        //given
        Url url = urlFactory.giveAGivenNumberOfUrls(1).get(0);

        //when
        Url urlFromDb = urlRepository.save(url.getUrl());

        //then
        assertThat(urlFromDb).isNotNull();
        assertThat(urlFromDb.getUrl()).isEqualTo(url.getUrl());
    }
}