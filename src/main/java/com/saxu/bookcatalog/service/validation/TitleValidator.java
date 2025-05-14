package com.saxu.bookcatalog.service.validation;

import com.saxu.bookcatalog.error.CommonException;
import com.saxu.bookcatalog.error.ErrorCode;
import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.repository.BookRepository;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class TitleValidator extends AbstractValidator {

    @Resource
    BookRepository bookRepository;

    @Override
    public void validate(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new CommonException(ErrorCode.IS_NULL);
        }

        if (book.getTitle().length() > 50) {
            throw new CommonException(ErrorCode.TITLE_FIELD_TOO_LONG);
        }

        if (book.getId() == null) {
            Book existingBook = bookRepository.findByTitle(book.getTitle());
            if (existingBook != null) {
                throw new CommonException(ErrorCode.BOOK_TITLE_EXISTS);
            }
        }

        doNextValidator(book);
    }
}