package com.example.library.factory;

import com.example.library.domain.model.Author;
import com.example.library.dto.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorFactory {

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }
}
