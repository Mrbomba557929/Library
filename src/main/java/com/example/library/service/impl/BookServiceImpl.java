package com.example.library.service.impl;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.business.NotFound;
import com.example.library.exception.business.SortingException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.service.AuthorService;
import com.example.library.service.GenreService;
import com.example.library.domain.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.specification.GenericFilter;
import com.example.library.specification.GenericSearchParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.library.exception.factory.ErrorMessage.NOT_FOUND_BOOK;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final GenericFilter<Book> filter;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(NOT_FOUND_BOOK)
                                .status(NOT_FOUND)
                                .link("BookServiceImpl/findById")
                                .build(NotFound.class)
                );
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAll(int page, int count, GenericSearchParameters parameters) {
        Specification<Book> specification = filter.filterBy(parameters).build();

        if (Objects.nonNull(parameters.sort())) {

            if (parameters.sort().field().equalsIgnoreCase("authors")) {
                return sortByFirstElementOfCollection(
                        bookRepository.findAll(specification, PageRequest.of(page, count)),
                        parameters.sort().field(),
                        "fio",
                        parameters.sort().direction()
                );
            }

            return bookRepository.findAll(specification, PageRequest.of(page, count, Sort.by(parameters.sort().direction(), parameters.sort().field())));
        }

        return bookRepository.findAll(specification, PageRequest.of(page, count));
    }

    @Override
    public List<BookCreationDate> getCreationDates() {
        return bookRepository.getCreationDates();
    }

    @Override
    public Book save(Book book) {
        book.setAuthors(authorService.saveAll(book.getAuthors()));
        book.setGenre(genreService.save(book.getGenre()));

        try {
            return bookRepository.save(book);
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .link("BookServiceImpl/save")
                    .build(FailedToSaveException.class);
        }
    }

    @Override
    public Book edit(Book source) {
        Optional<Book> bookOptional = bookRepository.findById(source.getId());

        if (bookOptional.isPresent()) {
            return save(source);
        }

        throw ErrorFactory.exceptionBuilder(NOT_FOUND_BOOK)
                .status(NOT_FOUND)
                .link("BookServiceImpl/edit")
                .build(NotFound.class);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> sortByFirstElementOfCollection(Page<Book> target, String collection, String fieldOfElement, Sort.Direction direction) {

        int numericDirection = direction == Sort.Direction.ASC ? 1 : -1;

        List<Book> sortedList = target.stream().sorted((it1, it2) -> {
            try {

                List<?> list1 = (List<?>) getValueField(it1, collection);
                List<?> list2 = (List<?>) getValueField(it2, collection);

                if (list1.isEmpty() && list2.isEmpty()) {
                    return 0;
                }

                if (list1.isEmpty() || list2.isEmpty()) {
                    return numericDirection * (list1.isEmpty() ? -1 : 1);
                }

                String firstName1 = (String) getValueField(list1.get(0), fieldOfElement);
                String firstName2 = (String) getValueField(list2.get(0), fieldOfElement);

                return numericDirection * firstName1.compareToIgnoreCase(firstName2);

            } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
                throw ErrorFactory.exceptionBuilder(e.getMessage())
                        .status(EXPECTATION_FAILED)
                        .link("BookServiceImpl/sortByFirstElementOfCollection")
                        .build(SortingException.class);
            }
        }).toList();

        AtomicInteger counter = new AtomicInteger();
        return target.map(item -> sortedList.get(counter.getAndIncrement()));
    }

    private Object getValueField(Object item, String field) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = item.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(item);
    }
}
