package com.i2303.coursework.transactions.CourseworkTransactions.controller;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.CategoryDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Получение всех категорий
    @GetMapping
    public String getAllCategories(Model model) {
        try {
            List<CategoryDTO> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "categories";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при получении списка категорий: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    // Метод для пакетного добавления категорий с возвратом ошибок
    @PostMapping("/batchWithErrors")
    public String addMultipleCategoriesWithErrors(@RequestParam String categories, Model model) {
        // Разделяем введенные категории по новой строке
        String[] categoriesArray = categories.split("\n");
        List<CategoryDTO> categoriesToAdd = new ArrayList<>();
        for (String categoryName : categoriesArray) {
            categoryName = categoryName.trim(); // Убираем лишние пробелы
            if (!categoryName.isEmpty()) {
                categoriesToAdd.add(new CategoryDTO(null, categoryName));
            }
        }

        List<String> errors = categoryService.addMultipleCategories(categoriesToAdd);

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("errorMessages", errors);
        return "categories";
    }

    // Удаление категории
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            categoryService.deleteCategory(id);
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при удалении категории: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}
