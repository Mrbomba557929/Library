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
    added_at    TIMESTAMP DEFAULT now(),
    user_id     INTEGER,
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
    role VARCHAR(20)
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    TEXT UNIQUE,
    password TEXT
);

CREATE TABLE refresh_tokens
(
    id          SERIAL PRIMARY KEY,
    token       TEXT UNIQUE,
    expiry_date TIMESTAMP,
    user_id     INTEGER
);

CREATE TABLE stats
(
    count BIGINT,
    date TIMESTAMP DEFAULT now()
);

ALTER TABLE books
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

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
