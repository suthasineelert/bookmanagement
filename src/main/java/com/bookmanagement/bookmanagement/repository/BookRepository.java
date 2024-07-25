package com.bookmanagement.bookmanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookmanagement.bookmanagement.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
