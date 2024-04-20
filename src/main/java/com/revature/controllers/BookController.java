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
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    @Autowired
    public BookController(BookDAO bookDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookDAO.findAll());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Object> getBook(@PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("Book does not exist.");
        }
        return ResponseEntity.ok().body(b.get());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getBookByUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        List<Book> bookList = bookDAO.findByUserUserId(userId);
        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Object> insertBook(@RequestBody Book book, @PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }
        book.setUser(u.get());
        Book b = bookDAO.save(book);
        return ResponseEntity.status(201).body(b);
    }

    // TODO: should probably request by book ID
    @PutMapping("/user/{userId}")
    public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }
        book.setUser(u.get());
        Book b = bookDAO.save(book);
        return ResponseEntity.ok(b);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<Object> updateBookTitle(@RequestBody String bookName, @PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);

        if(b.isEmpty()) {
            return ResponseEntity.status(404).body("No book found with ID of: " + bookId);
        }

        Book book = b.get();
        book.setTitle(bookName);
        bookDAO.save(book);

        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Object> deleteBook (@PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);
        if (b.isEmpty()) {
            return ResponseEntity.status(404).body("No book at ID " + bookId + "found");
        }

        Book book = b.get();

        bookDAO.deleteById(bookId);
        return ResponseEntity.ok().body(book.getTitle() + " deleted from Books");
    }

}
