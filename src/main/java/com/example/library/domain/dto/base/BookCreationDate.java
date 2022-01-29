package com.example.library.domain.dto.base;

import java.io.Serializable;
import java.time.LocalDate;

public record BookCreationDate(LocalDate creationAt) implements Serializable {
}
