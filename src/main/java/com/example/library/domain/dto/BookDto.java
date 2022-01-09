package com.example.library.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record BookDto(Long id,
                      @NotBlank(message = "Error: Name cannot be empty!")
                      @Size(max = 255, message = "Error: Name can be maximum 255 characters!")
                      String name,
                      @NotNull(message = "Error: The date of writing the book must be indicated!")
                      LocalDate creationAt,
                      @Size(min = 1, message = "Error: You must indicate at least one author of the book!")
                      List<AuthorDto> authors,
                      @NotNull(message = "Error: You must indicate the genre of the book!")
                      GenreDto genre,
                      UrlDto url) {

    @Builder
    @JsonCreator
    public BookDto(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @JsonProperty("creationAt") LocalDate creationAt,
                   @JsonProperty("authors") List<AuthorDto> authors, @JsonProperty("genre") GenreDto genre,
                   @JsonProperty("url") UrlDto url) {
        this.id = id;
        this.name = name;
        this.creationAt = creationAt;
        this.authors = authors;
        this.genre = genre;
        this.url = url;
    }
}
