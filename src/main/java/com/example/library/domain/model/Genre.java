package com.example.library.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "genre")
    private String genre;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "genre")
    private Set<Book> books;
}
