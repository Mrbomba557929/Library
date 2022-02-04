INSERT INTO book_search_stats (count, date)
VALUES
    (5, CURRENT_DATE),
    (10, CURRENT_DATE + INTERVAL '1 day'),
    (15, CURRENT_DATE + INTERVAL '2 day'),
    (22, CURRENT_DATE + INTERVAL '3 day'),
    (34, CURRENT_DATE + INTERVAL '4 day');
