package com.i2303.coursework.transactions.CourseworkTransactions.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(columnNames = "name"))  // Уникальность имени автора
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ограничение на поле name: не может быть пустым и должно содержать хотя бы 1 символ
    @NotBlank(message = "Имя автора не может быть пустым")
    @Size(min = 1, message = "Имя автора должно содержать хотя бы 1 символ")
    @Column(nullable = false, unique = true)  // Имя автора не может быть пустым и должно быть уникальным
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public Author() {}

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
