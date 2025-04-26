package com.i2303.coursework.transactions.CourseworkTransactions.repository;

import com.i2303.coursework.transactions.CourseworkTransactions.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
