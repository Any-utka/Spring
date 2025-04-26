package com.i2303.coursework.transactions.CourseworkTransactions.dto;

import com.i2303.coursework.transactions.CourseworkTransactions.entity.Publisher;

public class PublisherDTO {

    private Long id;
    private String name;

    // Конструктор по умолчанию
    public PublisherDTO() {}

    // Конструктор, принимающий сущность Publisher
    public PublisherDTO(Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
    }

    // Геттеры и сеттеры
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
}
