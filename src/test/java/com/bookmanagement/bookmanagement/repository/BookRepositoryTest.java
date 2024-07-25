package com.bookmanagement.bookmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bookmanagement.bookmanagement.model.Book;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        Book savedBook = bookRepository.save(book);
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        assertThat(retrievedBook).isPresent();
        assertThat(retrievedBook.get().getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void testFindAll() {
        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        book1.setAuthor("Test Author 1");

        Book book2 = new Book();
        book2.setTitle("Test Book 2");
        book2.setAuthor("Test Author 2");

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(2);
    }

    @Test
    public void testDeleteById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        Book savedBook = bookRepository.save(book);
        bookRepository.deleteById(savedBook.getId());

        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());

        assertThat(retrievedBook).isNotPresent();
    }
}
