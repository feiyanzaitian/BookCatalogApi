package com.saxu.bookcatalog.resource;

import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.service.factory.BookManagerComponentFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(BookResource.class)
class BookResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookManagerComponentFactory bookManagerComponentFactory;

    @Test
    void testBookApi() throws Exception {
        Book book = Book.builder().title("Title").author("Author").isbn("12345").build();
        when(bookManagerComponentFactory.getHandler(MessageOperationEnum.CREATE_BOOK).handler(any())).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Title\",\"author\":\"Author\",\"isbn\":\"12345\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = Book.builder().id(1L).title("Title").author("Author").isbn("12345").build();
        when(bookManagerComponentFactory.getHandler(MessageOperationEnum.GET_BOOK_BY_ID).handler(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.author", is("Author")));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book updatedBook = Book.builder().id(1L).title("UpdatedTitle").author("UpdatedAuthor").isbn("12345").build();
        when(bookManagerComponentFactory.getHandler(MessageOperationEnum.UPDATE_BOOK).handler(any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"UpdatedTitle\",\"author\":\"UpdatedAuthor\",\"isbn\":\"12345\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("UpdatedTitle")))
                .andExpect(jsonPath("$.author", is("UpdatedAuthor")));
    }

    @Test
    void testDeleteBook() throws Exception {
        when(bookManagerComponentFactory.getHandler(MessageOperationEnum.DELETE_BOOK).handler(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}