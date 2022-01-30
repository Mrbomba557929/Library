package com.example.library.service.impl;

import com.example.library.repository.BookStatsRepository;
import com.example.library.service.BookStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookStatsServiceImpl implements BookStatsService {

    private final BookStatsRepository bookStatsRepository;

    @Override
    public void increaseCounter() {
        bookStatsRepository.increaseCounter();
    }

    @Override
    public long getNumberOfAddedBooksForToday() {
        return bookStatsRepository.getNumberOfAddedBooksForToday();
    }

    @Override
    public long getNumberOfAddedBooksForWeek() {
        return bookStatsRepository.getNumberOfAddedBooksForWeek();
    }

    @Override
    public long getNumberOfAddedBooksForMonth() {
        return bookStatsRepository.getNumberOfAddedBooksForMonth();
    }

    @Override
    public long getNumberOfAddedBooksForYear() {
        return bookStatsRepository.getNumberOfAddedBooksForYear();
    }

    @Override
    public long getNumberOfSearchesBooksForToday() {
        return bookStatsRepository.getNumberOfSearchesBooksForToday();
    }

    @Override
    public long getNumberOfSearchesBooksForMonth() {
        return bookStatsRepository.getNumberOfSearchesBooksForMonth();
    }

    @Override
    public long getNumberOfSearchesBooksForYear() {
        return bookStatsRepository.getNumberOfSearchesBooksForYear();
    }

    @Override
    public long getNumberOfSearchesBooksForAllTime() {
        return bookStatsRepository.getNumberOfSearchesBooksForAllTime();
    }

    @Override
    public long getNumberOfAllBooks() {
        return bookStatsRepository.getCountAllBooks();
    }
}
