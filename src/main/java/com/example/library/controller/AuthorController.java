package com.example.library.controller;

import com.example.library.dto.AuthorDto;
import com.example.library.factory.AuthorFactory;
import com.example.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    private final static String FIND_ALL = "/authors";

    private final AuthorService authorService;
    private final AuthorFactory authorFactory;

    @GetMapping(FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<AuthorDto> authorDtos = authorService.findAll()
                .stream()
                .map(authorFactory::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }
}