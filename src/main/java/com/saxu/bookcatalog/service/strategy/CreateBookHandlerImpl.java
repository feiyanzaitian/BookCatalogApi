package com.saxu.bookcatalog.service.strategy;

import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.repository.BookRepository;
import com.saxu.bookcatalog.service.validation.ValidationChainHandler;
import org.springframework.stereotype.Service;

/**
 * @author saxu
 * @description
 * @date 2025/5/11 10:40:38
 */
@Service
public class CreateBookHandlerImpl implements BookHandler<Book, Book>{

    private final BookRepository bookRepository;

    private final ValidationChainHandler validationChainHandler;

    public CreateBookHandlerImpl(BookRepository bookRepository, ValidationChainHandler validationChainHandler) {
        this.bookRepository = bookRepository;
        this.validationChainHandler = validationChainHandler;
    }

    @Override
    public MessageOperationEnum getMessage() {
        return MessageOperationEnum.CREATE_BOOK;
    }

    @Override
    public Book handler(Book book) {
        validationChainHandler.executeValidation(book);
        return bookRepository.save(book);
    }
}
