package com.example.library.factory;

import com.example.library.domain.model.Url;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class UrlFactory {

    public List<Url> giveAGivenNumberOfUrls(int numberOfUrls) {
        return Stream.generate(
                () -> Url.builder()
                        .url(UUID.randomUUID().toString())
                        .build()
                )
                .limit(numberOfUrls)
                .toList();
    }
}
