package com.saxu.bookcatalog.service;

import com.saxu.bookcatalog.BookApiApplicationTests;
import com.saxu.bookcatalog.dto.BookUpdateReqDto;
import com.saxu.bookcatalog.error.CommonException;
import com.saxu.bookcatalog.error.ErrorCode;
import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.repository.BookRepository;
import com.saxu.bookcatalog.service.factory.BookManagerComponentFactory;
import com.saxu.bookcatalog.service.strategy.*;
import com.saxu.bookcatalog.service.validation.AbstractValidator;
import com.saxu.bookcatalog.service.validation.AuthorValidator;
import com.saxu.bookcatalog.service.validation.TitleValidator;
import com.saxu.bookcatalog.service.validation.ValidationChainHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest extends BookApiApplicationTests {

    private BookManagerComponentFactory factory;

    @Mock
    private BookRepository bookRepository;
    @Autowired
    private ValidationChainHandler validationChainHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        factory = new BookManagerComponentFactory(null);
        factory.registerHandler(MessageOperationEnum.GET_BOOK_BY_ID, new QueryBookHandlerImpl(bookRepository));
        factory.registerHandler(MessageOperationEnum.CREATE_BOOK, new CreateBookHandlerImpl(bookRepository, validationChainHandler));
        factory.registerHandler(MessageOperationEnum.UPDATE_BOOK, new UpdateBookHandlerImpl(bookRepository, validationChainHandler));
        factory.registerHandler(MessageOperationEnum.DELETE_BOOK, new DeleteBookHandlerImpl(bookRepository));
    }

    @Test
    void testGetBookById() {
        // 准备测试数据
        Book expectedBook = Book.builder().id(1L).title("Test Title").author("Test Author").isbn("12345").build();
        // 模拟行为
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));
        BookHandler<Long, Book> handler = factory.getHandler(MessageOperationEnum.GET_BOOK_BY_ID);
        // 执行测试
        Book actualBook = handler.handler(1L);
        // 验证结果
        assertNotNull(actualBook);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    void testCreateBook() {
        // 准备测试数据
        Book newBook = Book.builder().title("New Title").author("New Author").isbn("67890").build();
        Book savedBook = Book.builder().id(2L).title("New Title").author("New Author").isbn("67890").build();
        // 模拟行为
        when(bookRepository.save(newBook)).thenReturn(savedBook);

        BookHandler<Book, Book> handler = factory.getHandler(MessageOperationEnum.CREATE_BOOK);
        // 执行测试
        Book result = handler.handler(newBook);

        // 验证结果
        assertNotNull(result);
        assertEquals(savedBook, result);
    }

    @Test
    void testUpdateBook() {
        // 准备测试数据
        Book originalBook = Book.builder().id(2L).title("Old Title").author("Old Author").isbn("11111").build();
        BookUpdateReqDto bookUpdateReqDto = BookUpdateReqDto.builder().id(2L).book(Book.builder().title("New Title").author("New Author").isbn("11111").build()).build();
        
        // 模拟行为
        when(bookRepository.save(bookUpdateReqDto.getBook())).thenReturn(originalBook);
        BookHandler<BookUpdateReqDto, Book> handler = factory.getHandler(MessageOperationEnum.UPDATE_BOOK);
        // 执行测试
        Book result = handler.handler(bookUpdateReqDto);

        // 验证结果
        assertNotNull(result);
        assertEquals(originalBook, result);
    }

    @Test
    void testDeleteBook() {
        // 准备测试数据
        Long bookId = 4L;
        
        // 模拟行为
        doNothing().when(bookRepository).deleteById(bookId);
        BookHandler<Long, Void> handler = factory.getHandler(MessageOperationEnum.DELETE_BOOK);
        // 执行测试
        Void result = handler.handler(bookId);

        // 验证结果
        assertNull(result);
    }

    @Test
    void testAuthorValidatorWithNullAuthor() {
        Book invalidBook = Book.builder().author(null).build();
        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(invalidBook)
        );
        assertEquals(ErrorCode.IS_NULL, exception.getErrorCode());
    }

    @Test
    void testAuthorValidatorWithEmptyAuthor() {
        Book invalidBook = Book.builder().title("Test Title").author("").build();
        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(invalidBook)
        );
        assertEquals(ErrorCode.IS_NULL, exception.getErrorCode());
    }

    @Test
    void testAuthorValidatorWithLongAuthor() {
        Book invalidBook = Book.builder().title("Test Title").author("This author name is way too long to be valid").build();
        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(invalidBook)
        );
        assertEquals(ErrorCode.AUTHOR_FIELD_TOO_LONG, exception.getErrorCode());
    }

    // TitleValidator Tests

    @Test
    void testTitleValidatorWithNullTitle() {
        Book invalidBook = Book.builder().title(null).author("Author").build();
        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(invalidBook)
        );
        assertEquals(ErrorCode.IS_NULL, exception.getErrorCode());
    }

    @Test
    void testTitleValidatorWithEmptyTitle() {
        Book invalidBook = Book.builder().title("").author("Author").build();
        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(invalidBook)
        );
        assertEquals(ErrorCode.IS_NULL, exception.getErrorCode());
    }

    @Test
    void testTitleValidatorWithLongTitle() {
        Book invalidBook = Book.builder().title("This title is way too long to be valid and should throw an exception").author("Author").build();
        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(invalidBook)
        );
        assertEquals(ErrorCode.TITLE_FIELD_TOO_LONG, exception.getErrorCode());
    }

    @Test
    void testTitleValidatorWithDuplicateTitle() {
        Book book = Book.builder().title("Duplicate Title").author("Author").build();
        when(bookRepository.findByTitle(any())).thenReturn(book);

        CommonException exception = assertThrows(CommonException.class, () ->
                validationChainHandler.executeValidation(book)
        );
        assertEquals(ErrorCode.BOOK_TITLE_EXISTS, exception.getErrorCode());
    }

}