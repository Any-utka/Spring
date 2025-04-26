package com.i2303.coursework.transactions.CourseworkTransactions.repository;

import com.i2303.coursework.transactions.CourseworkTransactions.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}