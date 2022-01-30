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

INSERT INTO authors (fio)
VALUES
    ('Вася Пупкин'),
    ('Дима Дудкин'),
    ('Артём Муткин'),
    ('Алексей Бакукин');

INSERT INTO urls (url)
VALUES
    ('https://vk.com/im?peers=313873506_c19_c21'),
    ('https://vk.com/im?peers=534534543'),
    ('https://vk.com/im?peers=31387350867867876876');

INSERT INTO genres (genre)
VALUES
    ('Роман'),
    ('Путешествие'),
    ('Детектив'),
    ('Баракутин');

INSERT INTO books (name, creation_at, genre, added_at, url_id)
VALUES
    ('Пупка', '04-04-2004', 'Роман', '04-04-2004', 1),
    ('Дудка', '04-04-2004', 'Детектив', '04-04-2004', 2),
    ('Мутка', '04-04-2004', 'Путешествие', '04-04-2004', 3),
    ('Путка', '04-04-2004', 'Баракутин', '04-04-2004', 2);

INSERT INTO authors_books (author_id, book_id)
VALUES
    (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 3),
       (4, 4);