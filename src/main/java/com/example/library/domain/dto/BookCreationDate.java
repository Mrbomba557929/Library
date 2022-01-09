package com.example.library.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record BookCreationDate(LocalDate creationAt) implements Serializable {

    public BookCreationDate(LocalDate creationAt) {
        this.creationAt = creationAt;
    }
}
