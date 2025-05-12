package com.saxu.bookcatalog.service.strategy;

import com.saxu.bookcatalog.model.MessageOperationEnum;

public interface BookHandler<T, R> {

    MessageOperationEnum getMessage();

    R handler(T reqParam);
}
