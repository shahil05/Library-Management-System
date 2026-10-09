# Library Management System

A role-based library management web app built with Java, JDBC, MySQL, JSP and Servlets.

## Features
- Normalized 4-table MySQL schema (users, books, issue_records, borrowing_history)
- DAO pattern: model, data-access and servlet layers kept separate
- Book issue and return workflows using JDBC transactions (manual commit/rollback), with row locking (`SELECT ... FOR UPDATE`) to prevent double-issuing a copy
- Reporting queries using JOIN, GROUP BY and DATEDIFF (overdue books, most-borrowed books)
- Login with session-based, role-based access: separate dashboards for librarians and students, and pages that redirect anyone who is not logged in with the right role

## Tech stack
Java 17, JDBC, MySQL, JSP, Servlets (javax), Maven, Tomcat 7 (via the Maven plugin)

## Project structure
- `sql/schema.sql`: database schema
- `sql/reports.sql`: reporting queries
- `src/com/library/model`, `dao`, `servlet`, `util`: Java code
- `src/main/webapp`: JSP pages

## How to run
1. Run `sql/schema.sql` in MySQL to create the `library_management` database.
2. Copy `src/main/resources/db.properties.example` to `src/main/resources/db.properties` and put in your MySQL password.
3. Insert a test user, e.g.
   `INSERT INTO users (name, email, password, role) VALUES ('Test Student', 'test@student.com', 'password123', 'STUDENT');`
4. Run `mvn tomcat7:run` (or the `tomcat7:run` goal from the Maven panel in IntelliJ).
5. Open `http://localhost:8080/login.jsp`.

## Progress log
- Day 1: schema design (entity vs relationship tables, active loans vs permanent history)
- Day 2: JDBC connection utility, Book model, BookDAO with PreparedStatement
- Day 3: transactional issue/return workflow
- Day 4: reporting queries
- Day 5: Maven + Tomcat, login servlet with sessions, role-based dashboards, logout
- Day 6: access guards on dashboards, final README

## Known limitations / next steps
- Passwords are stored in plain text; a real system would hash them (e.g. BCrypt)
- Issue/return and reports currently run through test code and SQL; they are not yet wired to buttons on the dashboards
- Styling is minimal