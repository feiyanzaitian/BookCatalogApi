package com.saxu.bookcatalog.service;

import com.saxu.bookcatalog.BookApiApplicationTests;
import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.repository.BookRepository;
import com.saxu.bookcatalog.service.factory.BookManagerComponentFactory;
import com.saxu.bookcatalog.service.strategy.BookHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class BookServiceTest extends BookApiApplicationTests {
    @Autowired
    private BookManagerComponentFactory factory;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void testGetBookById() {
        // 先创建一本书
        Book book = Book.builder().title("ToQueryTitle").author("ToQueryAuthor").isbn("11111").build();
        BookHandler<Book, Book> createHandler = factory.getHandler(MessageOperationEnum.CREATE_BOOK);
        Book saved = createHandler.handler(book);

        // 获取查询处理器并执行查询
        BookHandler<Long, Book> getHandler = factory.getHandler(MessageOperationEnum.GET_BOOK_BY_ID);
        Book queriedBook = getHandler.handler(saved.getId());

        assertNotNull(queriedBook);
        assertEquals(saved.getId(), queriedBook.getId());
    }


    @Test
    void testCreateBook() {
        Book book = Book.builder().title("Title").author("Author").isbn("12345").build();
        BookHandler<Book, Book> bookHandler = factory.getHandler(MessageOperationEnum.CREATE_BOOK);
        Book saved = bookHandler.handler(book);
        assertNotNull(saved.getId());
    }

    @Test
    void testUpdateBook() {
        // 先创建一本书
        Book book = Book.builder().title("OldTitle").author("OldAuthor").isbn("22222").build();
        BookHandler<Book, Book> createHandler = factory.getHandler(MessageOperationEnum.CREATE_BOOK);
        Book saved = createHandler.handler(book);

        // 修改内容并调用更新处理器
        saved.setTitle("NewTitle");
        saved.setAuthor("NewAuthor");
        BookHandler<Book, Book> updateHandler = factory.getHandler(MessageOperationEnum.UPDATE_BOOK);
        Book updatedBook = updateHandler.handler(saved);

        assertNotNull(updatedBook);
        assertEquals("NewTitle", updatedBook.getTitle());
        assertEquals("NewAuthor", updatedBook.getAuthor());
    }

    @Test
    void testDeleteBook() {
        Book book = Book.builder().title("ToDeleteTitle").author("ToDeleteAuthor").isbn("67890").build();
        BookHandler<Book, Book> createHandler = factory.getHandler(MessageOperationEnum.CREATE_BOOK);
        Book saved = createHandler.handler(book);
        BookHandler<Long, Boolean> deleteHandler = factory.getHandler(MessageOperationEnum.DELETE_BOOK);
        Boolean isDeleted = deleteHandler.handler(saved.getId());
        assertNotNull(isDeleted);
    }


}