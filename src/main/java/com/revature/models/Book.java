package com.revature.models;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "books")
@Component
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // Constructors
    public Book() {
    }

    public Book(int bookId, User user, String title) {
        this.bookId = bookId;
        this.user = user;
        this.title = title;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Other
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", user=" + user +
                ", title='" + title + '\'' +
                '}';
    }
}
