package com.example.library.dtofactory;

import com.example.library.domain.model.Author;
import com.example.library.domain.dto.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorFactory {

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
