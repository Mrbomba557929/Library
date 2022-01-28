DROP TABLE products;
CREATE TABLE products
(
    id serial,
title varchar(100),
price numeric(6,2),
view int default(0)
);
INSERT INTO products(title, price)
VALUES  ('Products #1', 40),
        ('Products #2', 50),
        ('Products #3', 60),
        ('Products #4', 70),
        ('Products #5', 80),
        ('Products #6', 90),
        ('Products #7', 80),
        ('Products #8', 70),
        ('Products #9', 60),
        ('Products #10', 50),
        ('Products #11', 40),
        ('Products #12', 50),
        ('Products #13', 60),
        ('Products #14', 70),
        ('Bread #1', 80),
        ('Bread #2', 40);
