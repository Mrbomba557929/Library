CREATE TABLE genres (
    genre TEXT PRIMARY KEY
);

CREATE TABLE authors (
    first_name TEXT,
    last_name TEXT,
    PRIMARY KEY (first_name, last_name)
);

CREATE TABLE authors_books (
    author_id INTEGER,
    book_id INTEGER
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    name TEXT,
    creation_at TIMESTAMP,
    genre TEXT,
    added_at TIMESTAMP,
    url_id INTEGER
);

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    url TEXT
);

ALTER TABLE authors_books ADD FOREIGN KEY (author_id) REFERENCES authors (id);

ALTER TABLE authors_books ADD FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE books ADD FOREIGN KEY (genre) REFERENCES genres (genre);

ALTER TABLE books ADD FOREIGN KEY (url_id) REFERENCES urls (id);

INSERT INTO genres (genre)
VALUES
    ('путешествие'),
    ('роман'),
    ('детектив'),
    ('сказка');

INSERT INTO authors (first_name, last_name)
VALUES
    ('Вася', 'Пупкин'),
    ('Дима', 'Петькин'),
    ('Антон', 'Баракутин'),
    ('Витя', 'Мавроди');

INSERT INTO books (name, creation_at, added_at, genre_id)
VALUES
    ('Путь к горе', '2020-02-02', '2020-02-02', 1),
    ('Путь к стулу', '2020-02-02', '2020-02-02', 2),
    ('Путь к полу', '2020-02-02', '2020-02-02', 3),
    ('Путь к комнате', '2020-02-02', '2020-02-02', 4);

INSERT INTO authors_books (author_id, book_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);
