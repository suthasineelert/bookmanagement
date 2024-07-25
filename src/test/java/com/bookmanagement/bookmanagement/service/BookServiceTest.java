package com.bookmanagement.bookmanagement.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookmanagement.bookmanagement.model.Book;
import com.bookmanagement.bookmanagement.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testAddBook() {
        // Create a new Book object
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);
        assertEquals(book, savedBook);
    }

    @Test
    void testDeleteBook() {
        // Create a new Book object
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        boolean isDeleted = bookService.deleteBook(1L);
        assertEquals(true, isDeleted);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setAuthor("Author One");
        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setAuthor("Author Two");

        List<Book> bookList = Arrays.asList(
                book1, book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(bookList, allBooks);
    }

    @Test
    void testGetBookById() {
        // Create a new Book object
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);
        assertEquals(Optional.of(book), foundBook);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Update Book");
        book.setAuthor("Update Author");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Optional<Book> updateBook = bookService.updateBook(book.getId(), book);
        assertEquals(Optional.of(book), updateBook);
    }
}
