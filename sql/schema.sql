-- Library Management System - Database Schema
-- Design note: 4 tables split by entity vs relationship.
-- users, books = entities (exist independently)
-- issue_records = CURRENT active loans only
-- borrowing_history = PERMANENT record, written once a loan is returned
-- (kept separate so archiving/clearing an active loan never loses history)

CREATE DATABASE IF NOT EXISTS library_management;
USE library_management;

-- 1. USERS
-- Two roles share one table (not split into Librarian/Student tables)
-- because they share every attribute except role -- splitting would
-- just reintroduce duplication for no benefit.
CREATE TABLE users (
    user_id      INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    role         ENUM('LIBRARIAN', 'STUDENT') NOT NULL DEFAULT 'STUDENT',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. BOOKS
-- available_copies is tracked separately from total_copies so we can
-- check availability with a simple read, instead of counting active
-- issue_records every time someone wants to issue a book.
CREATE TABLE books (
    book_id           INT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(200) NOT NULL,
    author            VARCHAR(150) NOT NULL,
    isbn              VARCHAR(20) UNIQUE,
    total_copies      INT NOT NULL DEFAULT 1,
    available_copies  INT NOT NULL DEFAULT 1
);

-- 3. ISSUE_RECORDS (active loans only)
-- Foreign keys tie this relationship table back to both entities.
-- status lets us query "what's currently out" without a date comparison.
CREATE TABLE issue_records (
    issue_id     INT AUTO_INCREMENT PRIMARY KEY,
    book_id      INT NOT NULL,
    user_id      INT NOT NULL,
    issue_date   DATE NOT NULL,
    due_date     DATE NOT NULL,
    status       ENUM('ISSUED', 'RETURNED') NOT NULL DEFAULT 'ISSUED',
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 4. BORROWING_HISTORY (permanent log)
-- Written when a return happens (Day 3). Never updated or deleted,
-- so it's always a safe source for reports (Day 4: JOIN + GROUP BY + DATEDIFF).
CREATE TABLE borrowing_history (
    history_id    INT AUTO_INCREMENT PRIMARY KEY,
    book_id       INT NOT NULL,
    user_id       INT NOT NULL,
    issue_date    DATE NOT NULL,
    due_date      DATE NOT NULL,
    return_date   DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);