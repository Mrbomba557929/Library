package com.example.library.factory;

import com.example.library.domain.model.Url;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class UrlFactory {

    public static UrlGenerator generator(int numberOfUrls) {
        return new UrlGenerator(numberOfUrls);
    }

    public static class UrlGenerator {

        private final int numberOfUrls;

        private String url;

        public UrlGenerator(int numberOfUrls) {
            this.numberOfUrls = numberOfUrls;
        }

        public UrlGenerator url(String url) {
            this.url = url;
            return this;
        }

        public List<Url> generate() {
            return Stream.generate(
                    () -> Url.builder()
                            .url(Objects.requireNonNullElse(url, UUID.randomUUID().toString()))
                            .build()
                    )
                    .limit(numberOfUrls)
                    .toList();
        }
    }
}
