package com.example.library.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "genre")
    private String genre;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "genre")
    private Set<Book> books;
}
