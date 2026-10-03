# Library Management System

A role-based library management system built with Java, JDBC, MySQL, and JSP.

## Progress Log

### Day 1 — Schema Design
- Modeled the domain as 4 tables, split by entity vs relationship:
  - `users` — librarians and students
  - `books` — catalog
  - `issue_records` — currently active loans
  - `borrowing_history` — permanent record of completed loans
- Kept active loans and historical loans in separate tables so archiving
  a returned book never loses the audit trail needed for reporting.

### Day 2 — JDBC + DAO Layer
- Added `DBConnection` (single place that opens a connection, reads
  credentials from a git-ignored `db.properties` so passwords never hit GitHub)
- Added `Book` model (POJO) and `BookDAO` (Create/Read/Update/Delete for
  the `books` table) using `PreparedStatement` throughout to prevent SQL injection
- Added `TestBookDAO` as a manual smoke test for the DAO layer

### Day 3 — Issue/Return Workflow (Transactions)
- Added `IssueRecordDAO` with `issueBook()` and `returnBook()`, both
  wrapped in manual transactions (`setAutoCommit(false)`, `commit()`,
  `rollback()`) so a partial failure never leaves `available_copies`
  out of sync with `issue_records`
- `issueBook()` uses `SELECT ... FOR UPDATE` to lock the book row during
  the availability check, preventing two simultaneous issues of the same copy

### Day 4 — Reporting Queries
- `sql/reports.sql`: overdue books report (JOIN across issue_records,
  books, users + DATEDIFF to calculate days late)
- Most-borrowed books report (JOIN + GROUP BY + COUNT), deliberately
  reading from `borrowing_history` rather than `issue_records` since
  only the permanent log reflects books that have already been returned

*(more to come: JSP + sessions, role-based dashboards, Tailwind styling)*
