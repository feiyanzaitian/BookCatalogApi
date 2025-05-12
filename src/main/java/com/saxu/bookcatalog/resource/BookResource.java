package com.saxu.bookcatalog.resource;

import com.saxu.bookcatalog.dto.BookUpdateReqDto;
import com.saxu.bookcatalog.model.ApiResponse;
import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.service.factory.BookManagerComponentFactory;
import com.saxu.bookcatalog.service.strategy.BookHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookResource extends AbstractBookResource {

    private final BookManagerComponentFactory bookManagerComponentFactory;

    public BookResource(BookManagerComponentFactory bookManagerComponentFactory) {
        this.bookManagerComponentFactory = bookManagerComponentFactory;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        return execute(() -> {
                    BookHandler<Long, Book> bookHandler = bookManagerComponentFactory.getHandler(MessageOperationEnum.GET_BOOK_BY_ID);
                    return ApiResponse.success(bookHandler.handler(id));
                },
                MessageOperationEnum.GET_BOOK_BY_ID.getValue());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> create(@RequestBody Book book) {
        return execute(() -> {
                    BookHandler<Book, Book> bookHandler = bookManagerComponentFactory.getHandler(MessageOperationEnum.CREATE_BOOK);
                    return ApiResponse.success(bookHandler.handler(book));
                },
                MessageOperationEnum.CREATE_BOOK.getValue());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> update(@PathVariable Long id, @RequestBody Book book) {
        return execute(() -> {
                    BookUpdateReqDto bookUpdateReqDto = BookUpdateReqDto.builder().id(id).book(book).build();
                    BookHandler<BookUpdateReqDto, Book> bookHandler = bookManagerComponentFactory.getHandler(MessageOperationEnum.UPDATE_BOOK);
                    return ApiResponse.success(bookHandler.handler(bookUpdateReqDto));
                },
                MessageOperationEnum.UPDATE_BOOK.getValue());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return execute(() -> {
                    BookHandler<Long, Void> bookHandler = bookManagerComponentFactory.getHandler(MessageOperationEnum.DELETE_BOOK);
                    return ApiResponse.success(bookHandler.handler(id));
                },
                MessageOperationEnum.DELETE_BOOK.getValue());
    }

}