-- Library Management System - Reporting Queries (Day 4)
-- These demonstrate JOIN, DATEDIFF, and GROUP BY against the schema
-- designed on Day 1. Run against library_management.

-- 1. Overdue books report
-- Joins issue_records -> books -> users to turn raw IDs into readable
-- data, and uses DATEDIFF to calculate how late each loan is.
SELECT 
    issue_records.issue_id,
    books.title,
    users.name,
    issue_records.due_date,
    DATEDIFF(CURDATE(), issue_records.due_date) AS days_overdue
FROM issue_records
JOIN books ON issue_records.book_id = books.book_id
JOIN users ON issue_records.user_id = users.user_id
WHERE issue_records.status = 'ISSUED'
  AND DATEDIFF(CURDATE(), issue_records.due_date) > 0;


-- 2. Most-borrowed books report
-- Reads from borrowing_history (the permanent log), NOT issue_records
-- (active loans only) -- issue_records would undercount books that
-- have already been returned. This is the concrete payoff of the
-- Day 1 decision to keep active and historical loans in separate tables.
SELECT 
    books.title,
    COUNT(*) AS times_borrowed
FROM borrowing_history
JOIN books ON borrowing_history.book_id = books.book_id
GROUP BY books.book_id, books.title
ORDER BY times_borrowed DESC;
