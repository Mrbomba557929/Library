package com.example.library.service.impl;

import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import com.example.library.service.AuthorService;
import com.example.library.service.GenreService;
import com.example.library.service.UrlService;
import com.example.library.sort.CustomSort;
import com.example.library.specification.GenericFilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.exception.NotFoundBookException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.specification.GenericFilter;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final UrlService urlService;
    private final AuthorService authorService;
    private final GenericFilter<Book> genericFilter;
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
    public Page<Book> findAll(int page, int count, String sort, GenericFilterParameters filterParameters) {
        Specification<Book> specification = genericFilter.filterBy(filterParameters).build();

        if (!Strings.isNullOrEmpty(sort)) {
            String[] parameters = sort.split("\\s*,\\s*");
            Sort.Direction direction = Sort.Direction.fromString(parameters[1]);

            if (parameters[0].equalsIgnoreCase("authors")) {
                Page<Book> books = bookRepository.findAll(specification, PageRequest.of(page, count));
                return customSort.sort(books, direction, "authors", "firstName");
            }

            return bookRepository.findAll(specification, PageRequest.of(page, count, Sort.by(direction, parameters[0])));
        }

        return bookRepository.findAll(specification, PageRequest.of(page, count));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book edit(Book source) {
        // TODO: здесь ошибка!
        Book book = findById(source.getId());
        book.setUrl(urlService.save(source.getUrl()));
        book.setGenre(genreService.save(source.getGenre()));
        book.setAuthors(authorService.saveAll(source.getAuthors()));
        book.setName(source.getName());
        book.setCreatedAt(source.getCreatedAt());
        return save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
