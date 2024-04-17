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

    @PostMapping("/{userId}")
    public ResponseEntity<Book> insertBook(@RequestBody Book book, @PathVariable int userId) {
        User u = userDAO.findById(userId).get();
        book.setUser(u);
        Book b = bookDAO.save(book);
        return ResponseEntity.status(201).body(b);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable int userId) {
        User u = userDAO.findById(userId).get();
        book.setUser(u);
        Book b = bookDAO.save(book);
        return ResponseEntity.ok(b);
    }


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookDAO.findAll();
        return ResponseEntity.ok(books);
    }

    /*ask the guy: how do you differentiate between item id and user id in variables?
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookDAO.findAll();
        return ResponseEntity.ok(books);
    }
     */

    @GetMapping("/{userId}")
    public ResponseEntity<Book> getBookByName(@PathVariable Integer userId) {
        //moderately certain this returns a list
        System.out.println("get by user id ");
        System.out.println(bookDAO.findByUserUserId(userId));
        Book b = bookDAO.findByUserUserId(userId);
        if (b == null) {
            return ResponseEntity.notFound().build();
        }
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
