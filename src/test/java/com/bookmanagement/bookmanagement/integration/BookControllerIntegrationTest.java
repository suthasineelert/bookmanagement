package com.bookmanagement.bookmanagement.integration;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookmanagement.bookmanagement.BookmanagementApplication;
import com.bookmanagement.bookmanagement.constant.BookManagementConstant;
import com.bookmanagement.bookmanagement.model.Book;
import com.bookmanagement.bookmanagement.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BookmanagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath*:application-integrationtest.properties")
@TestInstance(Lifecycle.PER_CLASS)
public class BookControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ObjectMapper objectMapper;

  // Create books for test
  @BeforeAll
  private void createTestBook() {
    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Book One");
    book1.setAuthor("Author One");

    Book book2 = new Book();
    book1.setId(2L);
    book2.setTitle("Book Two");
    book2.setAuthor("Author Two");

    bookRepository.save(book1);
    bookRepository.save(book2);
  }

  @AfterAll
  private void deleteTestBooks() {
    bookRepository.deleteAll();
  }

  @Test
  public void whenGetBooks_thenStatus200()
      throws Exception {
    mvc.perform(get("/books")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].title", is("Book One")))
        .andExpect(jsonPath("$[1].title", is("Book Two")));
  }

  @Test
  public void whenGetBookById_thenStatus200()
      throws Exception {
    Long id = 1L;
    Optional<Book> book = bookRepository.findById(id);
    String title = book.map(b -> b.getTitle()).orElse(null);
    mvc.perform(get("/books/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title", is(title)));
  }

  @Test
  public void givenNotExistBookId_whenGetBookById_thenStatus404()
      throws Exception {
    mvc.perform(get("/books/12345")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void whenCreateBook_thenStatus201()
      throws Exception {
    Book book = new Book();
    book.setTitle("New Book");
    book.setAuthor("New Author");

    mvc.perform(post("/books")
        .content(objectMapper.writeValueAsString(book))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title", is("New Book")))
        .andExpect(jsonPath("$.author", is("New Author")));
  }

  @Test
  public void givenNoBody_whenCreateBook_thenStatus400()
      throws Exception {
    Book book = new Book();
    book.setTitle(null);
    mvc.perform(post("/books")
        .content("{}")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title", is(BookManagementConstant.TITLE_REQUIRED)));
  }

  @Test
  public void whenUpdateBookById_thenStatus200()
      throws Exception {
    Long id = 1L;
    Book book = new Book();
    String updateTitle = "Updated Book";
    book.setTitle(updateTitle);
    mvc.perform(put("/books/{id}", id)
        .content(objectMapper.writeValueAsString(book))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title", is(updateTitle)));

    // revert back to original title
    Book updatedBook = bookRepository.findById(id).get();
    updatedBook.setTitle("Book One");
    bookRepository.save(updatedBook);
  }

  @Test
  public void givenNotExistBookId_whenUpdateBookById_thenStatus404()
      throws Exception {
    Book book = new Book();
    String updateTitle = "Updated Book";
    book.setTitle(updateTitle);
    mvc.perform(put("/books/12345")
        .content(objectMapper.writeValueAsString(book))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void whenDeleteBookById_thenStatus204()
      throws Exception {
    mvc.perform(delete("/books/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void givenNotExistBookId_whenDeleteBookById_thenStatus404()
      throws Exception {
    mvc.perform(delete("/books/12345")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}