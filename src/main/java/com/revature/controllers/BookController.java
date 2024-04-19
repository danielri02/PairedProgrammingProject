package com.revature.controllers;

import com.revature.daos.BookDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Book;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    BookDAO bookDAO;
    UserDAO userDAO;

    @Autowired
    public BookController(BookDAO bookDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookDAO.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable int bookId) {
        Book b = bookDAO.findById(bookId).get();
        return ResponseEntity.ok().body(b);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBookByUser(@PathVariable int userId) {
        List<Book> bookList = bookDAO.findByUserUserId(userId);
        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Book> insertBook(@RequestBody Book book, @PathVariable int userId) {
        User u = userDAO.findById(userId).get();
        book.setUser(u);
        Book b = bookDAO.save(book);
        return ResponseEntity.status(201).body(b);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable int userId) {
        User u = userDAO.findById(userId).get();
        book.setUser(u);
        Book b = bookDAO.save(book);
        return ResponseEntity.ok(b);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<Object> updateBookName(@RequestBody String bookName, @PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);

        if(b.isEmpty()) {
            return ResponseEntity.status(404).body("No book found with ID of: " + bookId);
        }

        Book book = b.get();
        book.setBookName(bookName);
        bookDAO.save(book);

        return ResponseEntity.accepted().body(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Object> deleteBook (@PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("No book at ID " + bookId + "found");
        }

        Book book = b.get();

        bookDAO.deleteById(bookId);
        return ResponseEntity.accepted().body(book.getBookName() + " deleted from Books");
    }

}
