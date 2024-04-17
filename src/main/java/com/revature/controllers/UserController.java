package com.revature.controllers;

import com.revature.daos.UserDAO;
import com.revature.models.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserDAO userDAO;

    @Autowired
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping
    ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userDAO.findAll());
    }

    @GetMapping("/{userId}")
    ResponseEntity<User> getUser(@PathVariable int userId) {
        User u = userDAO.findById(userId).get();
        return ResponseEntity.ok().body(u);
    }

    @PostMapping
    ResponseEntity<User> insertUser(@RequestBody User user) {
        User u = userDAO.save(user);

        return ResponseEntity.status(201).body(u);
    }

    @PutMapping("/{userId}")
    ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User user) {
        User u = userDAO.findById(userId).get();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        userDAO.save(u);
        return ResponseEntity.accepted().body(u);
    }

    @DeleteMapping("{userId}")
    ResponseEntity<User> removeUser(@PathVariable int userId) {
        User u = userDAO.findById(userId).get();
        userDAO.deleteById(userId);
        return ResponseEntity.ok().body(u);
    }
}
