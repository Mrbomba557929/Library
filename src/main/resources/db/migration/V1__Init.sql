CREATE TABLE genres
(
    genre VARCHAR(100) PRIMARY KEY
);

CREATE TABLE authors
(
    id  SERIAL PRIMARY KEY,
    fio VARCHAR(355) UNIQUE
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
    name        VARCHAR(300),
    creation_at TIMESTAMP,
    genre       VARCHAR(100),
    added_at    TIMESTAMP DEFAULT now(),
    user_id     INTEGER,
    url_id      INTEGER
);

CREATE TABLE urls
(
    id  SERIAL PRIMARY KEY,
    url VARCHAR(500)
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
    email    VARCHAR(200) UNIQUE,
    password VARCHAR(200)
);

CREATE TABLE refresh_tokens
(
    id          SERIAL PRIMARY KEY,
    token       VARCHAR(200) UNIQUE,
    expiry_date TIMESTAMP,
    user_id     INTEGER
);

CREATE TABLE book_search_stats
(
    count BIGINT,
    date TIMESTAMP DEFAULT now() UNIQUE
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