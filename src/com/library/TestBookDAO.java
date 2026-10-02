package com.library;

import com.library.dao.IssueRecordDAO;
import com.library.dao.BookDAO;
import com.library.model.Book;
import java.util.List;

public class TestBookDAO {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 3, 3);
        boolean added = bookDAO.addBook(book);
        System.out.println("Book added: " + added);

        List<Book> allBooks = bookDAO.getAllBooks();
        System.out.println("All books in DB:");
        for (Book b : allBooks) {
            System.out.println("  " + b);
        }

        // --- Day 3: test issuing and returning a book ---
        IssueRecordDAO issueRecordDAO = new IssueRecordDAO();

        int testBookId = 1;
        int testUserId = 1;

        boolean issued = issueRecordDAO.issueBook(testBookId, testUserId);
        System.out.println("Book issued: " + issued);

        Book afterIssue = bookDAO.getBookById(testBookId);
        System.out.println("Available copies after issue: " + afterIssue.getAvailableCopies());

        // --- test returning the book ---
        boolean returned = issueRecordDAO.returnBook(1, testBookId);
        System.out.println("Book returned: " + returned);

        Book afterReturn = bookDAO.getBookById(testBookId);
        System.out.println("Available copies after return: " + afterReturn.getAvailableCopies());
    }
}