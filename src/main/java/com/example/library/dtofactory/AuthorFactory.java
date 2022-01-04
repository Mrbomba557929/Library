package com.example.library.dtofactory;

import com.example.library.domain.model.Author;
import com.example.library.domain.dto.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorFactory {

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public Author toEntity(AuthorDto authorDto) {
        return Author.builder()
                .firstName(authorDto.getFirstName())
                .lastName(authorDto.getLastName())
                .build();
    }
}
