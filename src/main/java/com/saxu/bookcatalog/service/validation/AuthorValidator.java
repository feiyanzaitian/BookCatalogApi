package com.saxu.bookcatalog.service.validation;

import com.saxu.bookcatalog.error.CommonException;
import com.saxu.bookcatalog.error.ErrorCode;
import com.saxu.bookcatalog.model.Book;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(200)
public class AuthorValidator extends AbstractValidator {

    @Override
    public void validate(Book book) {
        if (book == null || book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new CommonException(ErrorCode.IS_NULL);
        }

        if (book.getAuthor().length() > 20) {
            throw new CommonException(ErrorCode.AUTHOR_FIELD_TOO_LONG);
        }
        doNextValidator(book);
    }
}