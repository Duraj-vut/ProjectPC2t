CREATE DATABASE IF NOT EXISTS Books;

USE Books;

CREATE TABLE IF NOT EXISTS book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookName VARCHAR(255),
    author VARCHAR(255),
    releaseYear INT,
    accessibility BOOL,
    genre VARCHAR(255),
    recYear INT,
    class VARCHAR(20)
);