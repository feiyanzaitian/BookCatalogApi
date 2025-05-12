package com.saxu.bookcatalog.service.strategy;

import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.repository.BookRepository;
import com.saxu.bookcatalog.service.validation.ValidationChainHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author saxu
 * @description
 * @date 2025/5/11 10:40:38
 */
@Service
public class DeleteBookHandlerImpl implements BookHandler<Long, Void>{

    private final BookRepository bookRepository;

    public DeleteBookHandlerImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public MessageOperationEnum getMessage() {
        return MessageOperationEnum.DELETE_BOOK;
    }

    @Override
    public Void handler(Long id) {
        bookRepository.deleteById(id);
        return null;
    }
}
