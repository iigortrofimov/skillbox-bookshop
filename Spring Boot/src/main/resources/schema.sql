DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS authors_books;

CREATE TABLE books
(
  id       INT PRIMARY KEY,
  title    VARCHAR(250) NOT NULL,
  priceOld VARCHAR(250) DEFAULT NULL,
  price    VARCHAR(250) DEFAULT NULL
);

CREATE TABLE authors
(
  id        INT PRIMARY KEY,
  firstName VARCHAR(250) NOT NULL,
  lastName  VARCHAR(250) NOT NULL
);

CREATE TABLE authors_books
(
  book_id   INT NOT NULL,
  author_id INT NOT NULL,
  PRIMARY KEY (book_id, author_id)
);
