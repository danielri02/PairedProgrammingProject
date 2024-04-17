package com.revature.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "books")
@Component
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "UserId")
    private User user;

    @Column(nullable = false, unique = true)
    private String bookName;

    //other properties here if needed


    public Book() {
    }

    public Book(int bookId, User user, String bookName) {
        this.bookId = bookId;
        this.user = user;
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", user=" + user +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
