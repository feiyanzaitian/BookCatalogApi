package com.saxu.bookcatalog.service.factory;

import com.saxu.bookcatalog.model.MessageOperationEnum;
import com.saxu.bookcatalog.service.strategy.BookHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saxu
 * @description
 * @date 2025/5/11 10:34:05
 */

@Component
public class BookManagerComponentFactory {

    private final ApplicationContext applicationContext;

    public BookManagerComponentFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private final Map<MessageOperationEnum, BookHandler> handlers = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, BookHandler> handlerMap = applicationContext.getBeansOfType(BookHandler.class);

        if (handlerMap.isEmpty()) {
            return;
        }

        handlerMap.forEach((beanName, bookHandler) -> this.registerHandler(bookHandler.getMessage(), bookHandler));
    }

    public <T, R> void registerHandler(MessageOperationEnum type, BookHandler<T, R> bookHandler) {
        if (bookHandler == null) {
            return;
        }
        handlers.put(type,bookHandler);
    }


    public <T, R> BookHandler<T, R> getHandler(MessageOperationEnum type) {
        if(type == null){
            return null;
        }
        return handlers.get(type);
    }
}
