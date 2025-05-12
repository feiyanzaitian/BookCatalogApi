package com.saxu.bookcatalog.service.strategy;

import com.saxu.bookcatalog.model.Book;
import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * @author saxu
 * @description
 * @date 2025/5/11 10:40:38
 */
@Service
public class QueryBookHandlerImpl implements BookHandler<Long, Book>{

    private final BookRepository bookRepository;

    public QueryBookHandlerImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public MessageOperationEnum getMessage() {
        return MessageOperationEnum.GET_BOOK_BY_ID;
    }

    @Override
    public Book handler(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}
