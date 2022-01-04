package com.example.library.controller;

import com.example.library.domain.dto.GenreDto;
import com.example.library.dtofactory.GenreFactory;
import com.example.library.service.GenreService;
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
public class GenreController {

    private final static String FIND_ALL = "/genres";

    private final GenreService genreService;
    private final GenreFactory genreFactory;

    @GetMapping(FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<GenreDto> genreDtos = genreService.findAll()
                .stream()
                .map(genreFactory::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(genreDtos, HttpStatus.OK);
    }
}
