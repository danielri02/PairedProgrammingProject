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
@RequestMapping("/users")
public class UserController {

    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    @Autowired
    public UserController(UserDAO userDAO, BookDAO bookDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userDAO.findAll());
    }

    @GetMapping("/{userId}")
    ResponseEntity<Object> getUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        return ResponseEntity.ok().body(u.get());
    }

    @PostMapping
    ResponseEntity<User> insertUser(@RequestBody User user) {
        User u = userDAO.save(user);
        return ResponseEntity.status(201).body(u);
    }

    @PutMapping("/{userId}")
    ResponseEntity<Object> updateUser(@PathVariable int userId, @RequestBody User user) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        u.get().setFirstName(user.getFirstName());
        u.get().setLastName(user.getLastName());
        userDAO.save(u.get());
        return ResponseEntity.ok().body(u.get());
    }

    @PatchMapping("/{userId}")
    ResponseEntity<Object> updateUserName(@PathVariable int userId, @RequestBody User user) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        if (user.getFirstName() != null) {
            u.get().setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            u.get().setLastName(user.getLastName());
        }

        userDAO.save(u.get());
        return ResponseEntity.ok().body(u.get());
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        userDAO.deleteById(userId);
        return ResponseEntity.ok().body("Deleted user " +
                u.get().getFirstName() + " " + u.get().getLastName() + " and all associated books.");
    }

    /*
    ** Methods on user's book collection
    */

    @GetMapping("/{userId}/books")
    public ResponseEntity<Object> getBooksByUser(@PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.status(404).body("User does not exist.");
        }
        List<Book> bookList = bookDAO.findByUserUserId(userId);
        return ResponseEntity.ok().body(bookList);
    }

    @PostMapping("/{userId}/books")
    public ResponseEntity<Object> insertBook(@RequestBody Book book, @PathVariable int userId) {
        Optional<User> u = userDAO.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }
        book.setUser(u.get());
        Book b = bookDAO.save(book);
        return ResponseEntity.status(201).body(b);
    }
}
