package com.i2303.coursework.transactions.CourseworkTransactions.dto;

import java.util.List;

public class LibraryDTO {
    private Long id;
    private List<Long> bookIds;

    // Конструктор с аргументами
    public LibraryDTO(Long id, List<Long> bookIds) {
        this.id = id;
        this.bookIds = bookIds;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
