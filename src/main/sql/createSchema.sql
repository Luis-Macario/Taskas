DROP TABLE if EXISTS users cascade;
DROP TABLE if EXISTS boards cascade;
DROP TABLE if EXISTS taskLists cascade;
DROP TABLE if EXISTS cards cascade;
DROP TABLE if EXISTS userBoards cascade;

CREATE TABLE users
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(50) NOT NULL ,
    email varchar(60) NOT NULL CHECK (email SIMILAR TO '_%@_%')  UNIQUE,
    token CHAR(36) NOT NULL CHECK ( char_length(token) = 36 )
);

CREATE TABLE boards
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(100) UNIQUE NOT NULL CHECK ( char_length(name) > 20 ),
    description VARCHAR(1000) NOT NULL CHECK ( char_length(description) > 0 )
);

CREATE TABLE taskLists
(
    id SERIAL PRIMARY KEY ,
    bid INT references boards(id) ON DELETE CASCADE ON UPDATE CASCADE,
    name VARCHAR(100) NOT NULL CHECK ( char_length(name) > 3 ),
    archived BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE cards
(
    id SERIAL PRIMARY KEY,
    bid INT references boards(id) ON DELETE CASCADE ON UPDATE CASCADE,
    lid INT references taskLists(id) ON DELETE CASCADE ON UPDATE CASCADE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    initDate DATE NOT NULL,
    finishDate Date NOT NULL DEFAULT DATE('9999-12-31')
);

CREATE TABLE userBoards
(
    uid INT references users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    bid INT references boards(id) ON DELETE CASCADE ON UPDATE CASCADE
)