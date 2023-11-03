package com.swf.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    BookRepository repository;

    private ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    private Book firstBook;
    private Book secondBook;

    @BeforeEach
    void setUp() {
        this.firstBook = new Book()
                .setTitle("Harry Potter")
                .setAuthor("JK Rowling")
                .setFavorite(false);
        this.firstBook = this.repository.save(firstBook);

        this.secondBook = new Book()
                .setTitle("To Kill A Mockingbird")
                .setAuthor("Harper Lee")
                .setFavorite(true);
        this.secondBook = this.repository.save(secondBook);
    }

    @Test
    public void getAllBooksFromRepository() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books")
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())

                // Check firstBook
                .andExpect(jsonPath("$[0].id", Is.is(this.firstBook.getId().intValue())))
                .andExpect(jsonPath("$[0].title", Is.is(this.firstBook.getTitle())))
                .andExpect(jsonPath("$[0].author", Is.is(this.firstBook.getAuthor())))
                .andExpect(jsonPath("$[0].favorite", Is.is(this.firstBook.getFavorite())))

                // Check secondBook
                .andExpect(jsonPath("$[1].id", Is.is(this.secondBook.getId().intValue())))
                .andExpect(jsonPath("$[1].title", Is.is(this.secondBook.getTitle())))
                .andExpect(jsonPath("$[1].author", Is.is(this.secondBook.getAuthor())))
                .andExpect(jsonPath("$[1].favorite", Is.is(this.secondBook.getFavorite())));
    }

    @Test
    public void getBookByIdFromRepository() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/" + this.firstBook.getId())
                .accept(MediaType.APPLICATION_JSON);

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())

                // Check firstBook
                .andExpect(jsonPath("$.id", Is.is(this.firstBook.getId().intValue())))
                .andExpect(jsonPath("$.title", Is.is(this.firstBook.getTitle())))
                .andExpect(jsonPath("$.author", Is.is(this.firstBook.getAuthor())))
                .andExpect(jsonPath("$.favorite", Is.is(this.firstBook.getFavorite())));
    }

    @Test
    public void postCreatesBookWithValidDataRespondsWithJson() throws Exception {
        // Make a new book with default value (false) for favorite.
        Book newBook = new Book()
                .setTitle("The Great Gatsby")
                .setAuthor("F. Scott Fitzgerald");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newBook));

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())

                // Check newBook
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title", Is.is(newBook.getTitle())))
                .andExpect(jsonPath("$.author", Is.is(newBook.getAuthor())))
                .andExpect(jsonPath("$.favorite", Is.is(newBook.getFavorite())));
    }

    @Test
    public void patchUpdatesBookWithValidData() throws Exception {
        // Make an updated book to send as JSON to the controller.
        Book updatedBook = new Book()
                .setAuthor("FAKE NEWS")
                // Don't send the favorite field in JSON.
                .setFavorite(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/books/" + this.firstBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook));

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())

                // Check newBook
                .andExpect(jsonPath("$.id", Is.is(this.firstBook.getId().intValue())))
                .andExpect(jsonPath("$.title", Is.is(this.firstBook.getTitle())))
                .andExpect(jsonPath("$.author", Is.is(updatedBook.getAuthor())))
                .andExpect(jsonPath("$.favorite", Is.is(this.firstBook.getFavorite())));
    }

    @Test
    public void deleteBookFromRepository() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/" + this.firstBook.getId());

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS"));
    }
}