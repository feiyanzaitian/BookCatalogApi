package com.saxu.bookcatalog.service.validation;

import com.saxu.bookcatalog.model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationChainHandler {

    @Autowired
    private List<AbstractValidator> validators;

    private AbstractValidator headValidator;


    @PostConstruct
    public void buildChain() {
        if (validators == null || validators.isEmpty()) {
            return;
        }

        headValidator = validators.get(0);

        for (int i = 0; i < validators.size(); i++) {
            if (i == validators.size() - 1) {
                validators.get(i).setNextValidator(null);
            } else {
                validators.get(i).setNextValidator(validators.get(i + 1));
            }
        }
    }

    public void executeValidation(Book book) {
        headValidator.validate(book);
    }
}