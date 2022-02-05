package com.example.library.service;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.business.NotFound;
import com.example.library.exception.business.SortingException;
import com.example.library.factory.AuthorFactory;
import com.example.library.factory.BookFactory;
import com.example.library.repository.BookRepository;
import com.example.library.service.impl.BookServiceImpl;
import com.example.library.specification.GenericFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock private BookRepository bookRepository;
    @Mock private GenreService genreService;
    @Mock private AuthorService authorService;
    @Mock private GenericFilter<Book> filter;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, genreService, authorService, filter);
    }

    @DisplayName("Test should properly find the book by the id")
    @Test
    public void shouldProperlyFindTheBookByTheId() {
        //given
        Long id = 1L;
        Book book = BookFactory.generator(1).generate().get(0);
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        //when
        Book expected = bookService.findById(id);

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getName()).isEqualTo(book.getName());
        assertThat(expected.getAddedAt()).isEqualTo(book.getAddedAt());
        assertThat(expected.getCreationAt()).isEqualTo(book.getCreationAt());
        verify(bookRepository, Mockito.times(1)).findById(id);
    }

    @DisplayName("Test should fail when find the book by the id")
    @Test
    public void shouldFailWhenFindTheBookByTheId() {
        //given
        Long id = 1L;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> bookService.findById(id)).isInstanceOf(NotFound.class);
    }

    @DisplayName("Test should properly find all books")
    @Test
    void shouldProperlyFindAllBooks() {
        //given
        List<Book> books = BookFactory.generator(10).generate();
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        //when
        List<Book> expected = bookService.findAll();

        //then
        verify(bookRepository, Mockito.times(1)).findAll();
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(books.size());
    }

    @DisplayName("Test should properly sort by first element of list DESC")
    @Test
    void shouldProperlySortByFirstElementOfListDESC() {
        //given
        List<Book> books = BookFactory
                .generator(5)
                .generateWithAuthorsAndSortByFirstElementOfAuthors(
                        AuthorFactory.generator(1),
                        Sort.Direction.DESC
                );

        //when
        List<Book> sortedBooks = bookService.sortByFirstElementOfCollection(
                new PageImpl<>(books),
                "authors",
                "fio",
                Sort.Direction.DESC
        ).toList();

        //then
        assertThat(sortedBooks).isNotNull();
        assertThat(sortedBooks.size()).isEqualTo(books.size());
        assertThat(sortedBooks.get(0).getName()).isEqualTo(books.get(0).getName());
        assertThat(sortedBooks.get(1).getName()).isEqualTo(books.get(1).getName());
        assertThat(sortedBooks.get(2).getName()).isEqualTo(books.get(2).getName());
        assertThat(sortedBooks.get(3).getName()).isEqualTo(books.get(3).getName());
        assertThat(sortedBooks.get(4).getName()).isEqualTo(books.get(4).getName());
    }

    @DisplayName("Test should properly sort by first element of list ASC")
    @Test
    void shouldProperlySortByFirstElementOfListASC() {
        //given
        List<Book> books = BookFactory
                .generator(5)
                .generateWithAuthorsAndSortByFirstElementOfAuthors(
                        AuthorFactory.generator(1),
                        Sort.Direction.ASC
                );

        //when
        List<Book> sortedBooks = bookService.sortByFirstElementOfCollection(
                new PageImpl<>(books),
                "authors",
                "fio",
                Sort.Direction.ASC
        ).toList();

        //then
        assertThat(sortedBooks).isNotNull();
        assertThat(sortedBooks.size()).isEqualTo(books.size());
        assertThat(sortedBooks.get(0).getName()).isEqualTo(books.get(0).getName());
        assertThat(sortedBooks.get(1).getName()).isEqualTo(books.get(1).getName());
        assertThat(sortedBooks.get(2).getName()).isEqualTo(books.get(2).getName());
        assertThat(sortedBooks.get(3).getName()).isEqualTo(books.get(3).getName());
        assertThat(sortedBooks.get(4).getName()).isEqualTo(books.get(4).getName());
    }

    @DisplayName("Test should fail when sort by non existent element of list")
    @Test
    void shouldFailWhenSortByNonExistentElementOfList() {
        //given
        List<Book> books = BookFactory
                .generator(5)
                .generateWithAuthors(AuthorFactory.generator(1));

        //then
        assertThatThrownBy(
        () ->   bookService.sortByFirstElementOfCollection(
                        new PageImpl<>(books),
                        "non-existent",
                        "non-existent",
                        Sort.Direction.ASC
                ).toList()
        ).isInstanceOf(SortingException.class);
    }

    @DisplayName("Test should properly return creation dates")
    @Test
    void shouldProperlyReturnCreationDates() {
        //given
        List<BookCreationDate> creationDates = List.of(
                new BookCreationDate(LocalDate.now()),
                new BookCreationDate(LocalDate.now()),
                new BookCreationDate(LocalDate.now())
        );
        Mockito.when(bookRepository.getCreationDates()).thenReturn(creationDates);

        //when
        List<BookCreationDate> expected = bookService.getCreationDates();

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(creationDates.size());
    }

    @DisplayName("Test should properly save the book")
    @Test
    void shouldProperlySaveTheBook() {
        //given
        Book book = BookFactory.generator(1).generate().get(0);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);

        //when
        Book savedBook = bookService.save(book);

        //then
        verify(bookRepository).save(captor.capture());

        Book capturedValue = captor.getValue();

        assertThat(savedBook).isNotNull();
        assertThat(book).isEqualTo(capturedValue);
        assertThat(book).isEqualTo(savedBook);
    }

    @DisplayName("Test should fail when save already saved the book")
    @Test
    void shouldFailWhenSaveAlreadySavedTheBook() {
        //given
        Book book = BookFactory.generator(1).generate().get(0);
        Mockito.doThrow(FailedToSaveException.class).when(bookRepository).save(book);

        //then
        assertThatThrownBy(() -> bookService.save(book)).isInstanceOf(FailedToSaveException.class);
    }

    @DisplayName("Test should properly edit the book without errors")
    @Test
    void shouldProperlyEditWithoutErrors() {
        //given
        Book book = BookFactory.generator(1).generate().get(0);
        book.setId(1L);
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookService.save(book)).thenReturn(book);
        
        //when
        Book editedBook = bookService.edit(book);

        //then
        assertThat(editedBook).isNotNull();
        assertThat(editedBook).isEqualTo(book);
    }

    @DisplayName("Test should fail when edited non existent the book")
    @Test
    void shouldFailWhenEditedNonExistentBook() {
        //given
        Book book = BookFactory.generator(1).generate().get(0);
        book.setId(1L);
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> bookService.edit(book)).isInstanceOf(NotFound.class);
    }

    @DisplayName("Test should properly delete the book by the id")
    @Test
    void shouldProperlyDeleteTheBookByTheId() {
        //given
        Long id = 1L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        //when
        bookService.deleteById(id);

        //then
        verify(bookRepository).deleteById(captor.capture());

        Long captorValue = captor.getValue();

        assertThat(captorValue).isEqualTo(id);
    }
}