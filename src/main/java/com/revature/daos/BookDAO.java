package com.revature.daos;

import com.revature.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDAO  extends JpaRepository<Book, Integer> {
    public List<Book> findByUserUserId(int userId);
}
