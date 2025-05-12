package com.saxu.bookcatalog.repository;

import com.saxu.bookcatalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author saxu
 * @description
 * @date 2025/5/10 17:29:06
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);
}
