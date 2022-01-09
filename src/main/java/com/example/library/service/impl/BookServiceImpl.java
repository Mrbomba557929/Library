package com.example.library.service.impl;

import com.example.library.domain.dto.BookCreationDate;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import com.example.library.service.AuthorService;
import com.example.library.service.GenreService;
import com.example.library.service.UrlService;
import com.example.library.sort.CustomSort;
import com.example.library.domain.model.Book;
import com.example.library.exception.NotFoundBookException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.specification.GenericFilter;
import com.example.library.specification.GenericSearchParameters;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final UrlService urlService;
    private final AuthorService authorService;
    private final GenericFilter<Book> filter;
    private final CustomSort<Book> customSort;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> ErrorFactory.exceptionBuilder(ErrorMessage.NOT_FOUND_BOOK)
                        .status(HttpStatus.NOT_FOUND)
                        .build(NotFoundBookException.class));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAll(int page, int count, GenericSearchParameters parameters) {
        Specification<Book> specification = filter.filterBy(parameters).build();

        if (!Strings.isNullOrEmpty(parameters.sort())) {
            String[] sortParameters = parameters.sort().split("\\s*,\\s*");
            Sort.Direction direction = Sort.Direction.fromString(sortParameters[1]);

            if (sortParameters[0].equalsIgnoreCase("authors")) {
                Page<Book> books = bookRepository.findAll(specification, PageRequest.of(page, count));
                return customSort.sort(books, direction, "authors", "firstName");
            }

            return bookRepository.findAll(specification, PageRequest.of(page, count, Sort.by(direction, sortParameters[0])));
        }

        return bookRepository.findAll(specification, PageRequest.of(page, count));
    }

    @Override
    public List<BookCreationDate> getCreationDates() {
        return bookRepository.getCreationDates();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book edit(Book source) {
        Book book = findById(source.getId());
        book.setUrl(Objects.isNull(source.getUrl()) ? book.getUrl() : urlService.save(source.getUrl()));
        book.setGenre(genreService.save(source.getGenre()));
        book.setAuthors(authorService.saveAll(source.getAuthors()));
        book.setName(source.getName());
        book.setCreationAt(source.getCreationAt());
        return save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
