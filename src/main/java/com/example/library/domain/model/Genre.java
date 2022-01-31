package com.example.library.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Genre genre1 = (Genre) o;
        return genre != null && Objects.equals(genre, genre1.genre);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
