package com.library;

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
    }
}