package com.i2303.coursework.transactions.CourseworkTransactions.dto;

import com.i2303.coursework.transactions.CourseworkTransactions.entity.Book;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Category;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDTO {

    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
    private Long publisherId;
    private String publisherName;  // Добавлено
    private Set<Long> categoryIds;
    private Set<String> categoryNames;

    public BookDTO() {}

    public BookDTO(Long id, String title, Long authorId, Long publisherId, Set<Long> categoryIds) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.categoryIds = categoryIds != null ? categoryIds : Collections.emptySet();
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorId = book.getAuthor().getId();
        this.authorName = book.getAuthor().getName();
        this.publisherId = book.getPublisher().getId();
        this.publisherName = book.getPublisher().getName();  // Добавлено
        this.categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        this.categoryNames = book.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;  // Геттер для имени издателя
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds != null ? categoryIds : Collections.emptySet();
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds != null ? categoryIds : Collections.emptySet();
    }

    public Set<String> getCategoryNames() {
        return categoryNames != null ? categoryNames : Collections.emptySet();
    }

    public void setCategoryNames(Set<String> categoryNames) {
        this.categoryNames = categoryNames != null ? categoryNames : Collections.emptySet();
    }
}
