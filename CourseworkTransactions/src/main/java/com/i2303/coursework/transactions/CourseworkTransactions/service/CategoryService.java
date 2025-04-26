package com.i2303.coursework.transactions.CourseworkTransactions.service;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.CategoryDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Category;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Метод для добавления одной категории
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        String name = categoryDTO.getName();

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название категории не может быть пустым");
        }

        if (categoryRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Категория с названием '" + name + "' уже существует");
        }

        try {
            Category category = new Category(name);
            Category saved = categoryRepository.save(category);
            logger.info("Категория '{}' успешно добавлена", name);
            return new CategoryDTO(saved);
        } catch (Exception e) {
            logger.error("Ошибка при добавлении категории '{}'", name, e);
            throw e;
        }
    }

    // Метод для добавления нескольких категорий с возвратом ошибок
    public List<String> addMultipleCategories(List<CategoryDTO> categoryDTOList) {
        List<String> errors = new ArrayList<>();

        for (CategoryDTO categoryDTO : categoryDTOList) {
            try {
                addCategory(categoryDTO); // если ошибка — логируем, но продолжаем
            } catch (Exception e) {
                String name = categoryDTO.getName();
                errors.add("Категория '" + name + "' не добавлена: " + e.getMessage());
                logger.warn("Категория '{}' не добавлена: {}", name, e.getMessage());
            }
        }

        return errors;
    }

    // Метод для добавления нескольких категорий без возврата ошибок
    public void addMultipleCategoriesWithoutReturningErrors(List<CategoryDTO> categoryDTOList) {
        for (CategoryDTO categoryDTO : categoryDTOList) {
            try {
                addCategory(categoryDTO); // если ошибка — игнорируем, продолжаем
            } catch (Exception e) {
                logger.warn("Пропущена категория '{}': {}", categoryDTO.getName(), e.getMessage());
            }
        }
    }

    // Метод для получения всех категорий
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    // Метод для удаления категории
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}
