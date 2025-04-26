package com.i2303.coursework.transactions.CourseworkTransactions.repository;

import com.i2303.coursework.transactions.CourseworkTransactions.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Метод для поиска автора по имени
    Optional<Author> findByName(String name);

    // Метод для проверки существования автора с таким именем
    boolean existsByName(String name);
}
