package com.example.library.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {

    @NotBlank(message = "Error: Name cannot be empty!")
    @Size(max = 255, message = "Error: Name can be maximum 255 characters!")
    private String name;

    @NotNull(message = "Error: The date of writing the book must be indicated!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Size(min = 1, message = "Error: You must indicate at least one author of the book!")
    private List<AuthorDto> authors;

    @NotNull(message = "Error: You must indicate the genre of the book!")
    private GenreDto genre;

    private UrlDto url;
}
