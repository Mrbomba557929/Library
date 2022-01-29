package com.example.library.mapper;

import com.example.library.domain.model.Author;
import com.example.library.domain.dto.base.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .fio(author.getFio())
                .build();
    }

    public Author toEntity(AuthorDto authorDto) {
        return Author.builder()
                .fio(authorDto.fio())
                .build();
    }
}
