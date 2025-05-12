package com.saxu.bookcatalog.service.strategy;

import com.saxu.bookcatalog.dto.BookUpdateReqDto;
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
public class UpdateBookHandlerImpl implements BookHandler<BookUpdateReqDto, Book>{

    private final BookRepository bookRepository;

    private final ValidationChainHandler validationChainHandler;

    public UpdateBookHandlerImpl(BookRepository bookRepository, ValidationChainHandler validationChainHandler) {
        this.bookRepository = bookRepository;
        this.validationChainHandler = validationChainHandler;
    }

    @Override
    public MessageOperationEnum getMessage() {
        return MessageOperationEnum.UPDATE_BOOK;
    }

    @Override
    public Book handler(BookUpdateReqDto bookUpdateReqDto) {
        Book book = bookUpdateReqDto.getBook();
        book.setId(bookUpdateReqDto.getId());
        validationChainHandler.executeValidation(book);
        return bookRepository.save(book);
    }
}
