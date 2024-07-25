package com.bookmanagement.bookmanagement.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookmanagement.bookmanagement.model.Book;
import com.bookmanagement.bookmanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book One");
        book1.setAuthor("Author One");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book Two");
        book2.setAuthor("Author Two");

        bookList = Arrays.asList(book1, book2);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        given(bookService.getAllBooks()).willReturn(bookList);

        mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = bookList.get(0);

        when(bookService.getBookById(book.getId())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book");
        updatedBook.setAuthor("Updated Author");

        when(bookService.updateBook(Mockito.eq(1L), Mockito.any(Book.class))).thenReturn(Optional.of(updatedBook));

        mockMvc.perform(put("/books/1")
                .content(objectMapper.writeValueAsString(updatedBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedBook.getId()))
                .andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
                .andExpect(jsonPath("$.author").value(updatedBook.getAuthor()));
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book");
        updatedBook.setAuthor("Updated Author");

        when(bookService.updateBook(Mockito.anyLong(), Mockito.any(Book.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/1")
                .content(objectMapper.writeValueAsString(updatedBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(false);

        mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
