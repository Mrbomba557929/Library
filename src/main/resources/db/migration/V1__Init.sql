CREATE TABLE genres (
    genre TEXT PRIMARY KEY
);

CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    UNIQUE (first_name, last_name)
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
