CREATE TABLE genres
(
    genre TEXT PRIMARY KEY
);

CREATE TABLE authors
(
    id  SERIAL PRIMARY KEY,
    fio TEXT UNIQUE
);

CREATE TABLE authors_books
(
    author_id INTEGER,
    book_id   INTEGER,
    PRIMARY KEY (author_id, book_id)
);

CREATE TABLE books
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    creation_at TIMESTAMP,
    genre       TEXT,
    added_at    TIMESTAMP,
    url_id      INTEGER
);

CREATE TABLE urls
(
    id  SERIAL PRIMARY KEY,
    url TEXT
);

CREATE TABLE users_authorities
(
    user_id      INTEGER,
    authority_id INTEGER,
    PRIMARY KEY (user_id, authority_id)
);

CREATE TABLE authorities
(
    id   SERIAL PRIMARY KEY,
    role TEXT CHECK (length(lower(20)))
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    TEXT,
    password TEXT
);

CREATE TABLE refresh_tokens
(
    id          SERIAL PRIMARY KEY,
    token       TEXT UNIQUE,
    expiry_date TIMESTAMP,
    user_id     INTEGER
);

ALTER TABLE refresh_tokens
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_authorities
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users_authorities
    ADD FOREIGN KEY (authority_id) REFERENCES authorities (id);

ALTER TABLE authors_books
    ADD FOREIGN KEY (author_id) REFERENCES authors (id);

ALTER TABLE authors_books
    ADD FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE books
    ADD FOREIGN KEY (genre) REFERENCES genres (genre);

ALTER TABLE books
    ADD FOREIGN KEY (url_id) REFERENCES urls (id)
        ON DELETE CASCADE;
