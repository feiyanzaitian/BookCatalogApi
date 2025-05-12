package com.saxu.bookcatalog.service.validation;

import com.saxu.bookcatalog.model.Book;

public abstract class AbstractValidator {

    private AbstractValidator nextValidator;

    abstract void validate(Book book);

    protected void doNextValidator(Book book) {
        if (nextValidator != null) {
            nextValidator.validate(book);
        }
    }

    public void setNextValidator(AbstractValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

}