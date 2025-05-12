package com.saxu.bookcatalog.dto;

import com.saxu.bookcatalog.model.Book;
import lombok.Builder;
import lombok.Data;

/**
 * @author saxu
 * @description
 * @date 2025/5/12 11:06:13
 */
@Data
@Builder
public class BookUpdateReqDto {

    Long id;

    Book book;
}
