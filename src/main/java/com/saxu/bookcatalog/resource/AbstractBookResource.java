package com.saxu.bookcatalog.resource;

import com.saxu.bookcatalog.error.CommonException;
import com.saxu.bookcatalog.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;


public abstract class AbstractBookResource {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBookResource.class);

    public <T> ResponseEntity<ApiResponse<T>> execute(final Supplier<ApiResponse<T>> supplier, final String apiName) {
        long start = System.currentTimeMillis();
        try {
            ApiResponse<T> response = supplier.get();
            return ResponseEntity.ok(response);
        } catch (CommonException ce) {
            logger.error("Error occurred in API: {}", apiName, ce);
            ApiResponse<T> errorResponse = ApiResponse.error(ce.getMessage(), ce.getErrorCode().getErrorType().getHttpCode());
            return ResponseEntity.status(ce.getErrorCode().getErrorType().getHttpCode()).body(errorResponse);
        } finally {
            logger.info("{} executed in {} ms", apiName, (System.currentTimeMillis() - start));
        }
    }
}