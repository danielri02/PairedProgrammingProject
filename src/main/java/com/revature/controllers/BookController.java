package com.revature.controllers;

import com.revature.daos.BookDAO;
import com.revature.daos.UserDAO;
import com.revature.models.Book;
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

    @PutMapping("/{bookId}")
    public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);
        if (b.isEmpty()) {
            return ResponseEntity.badRequest().body("Book does not exist.");
        }
        b.get().setTitle(book.getTitle());
        b.get().setAuthor(book.getAuthor());
        bookDAO.save(book);
        return ResponseEntity.ok().body(b.get());
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<Object> patchBook(@RequestBody Book book, @PathVariable int bookId) {
        Optional<Book> b = bookDAO.findById(bookId);
        if(b.isEmpty()) {
            return ResponseEntity.status(404).body("No book found with ID of: " + bookId);
        }
        if (book.getTitle() != null) {
            b.get().setTitle(book.getTitle());
        }
        if (book.getAuthor() != null) {
            b.get().setAuthor(book.getAuthor());
        }
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
