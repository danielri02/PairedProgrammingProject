package com.revature.controllers;

import com.revature.daos.BookDAO;
import com.revature.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<Book> insertBook(@RequestBody Book book, @PathVariable int authorId) {
        Author a = authorDAO.findById(authorId).get();
        book.setAuthor(a);
        Book b = bookDAO.save(book);
        return ResponseEntity.status(201).body(b);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable int authorId) {
        Author a = authorDAO.findById(authorId).get();
        book.setAuthor(a);
        Book b = bookDAO.save(book);
        return ResponseEntity.ok(g);
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

    @GetMapping("/{authorId}")
    public ResponseEntity<Book> getBookByName(@PathVariable Integer authorId) {
        //moderately certain this returns a list
        System.out.println("get by author id ");
        System.out.println(bookDAO.findByAuthorAuthorId(authorId));
        Book b = bookDAO.findByAuthorAuthorId(authorId);
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
