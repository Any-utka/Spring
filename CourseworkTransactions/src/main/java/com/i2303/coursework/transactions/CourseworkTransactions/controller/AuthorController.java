package com.i2303.coursework.transactions.CourseworkTransactions.controller;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.AuthorDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.service.AuthorIsolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorIsolationService authorIsolationService;

    @Autowired
    public AuthorController(AuthorIsolationService authorIsolationService) {
        this.authorIsolationService = authorIsolationService;
    }

    // Отображение всех авторов
    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorIsolationService.getAllAuthors());
        model.addAttribute("editingId", null);
        return "authors";
    }

    // Добавление нескольких авторов
    @PostMapping("/multiple")
    public String addMultipleAuthors(@RequestParam("names") String names, Model model) {
        List<AuthorDTO> authors = names.lines()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(name -> {
                    AuthorDTO dto = new AuthorDTO();
                    dto.setName(name);
                    return dto;
                })

                .toList();

        try {
            authorIsolationService.addMultipleAuthors(authors);
            model.addAttribute("successMessage", "Авторы успешно добавлены!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Ошибка: один из предложенных авторов уже есть в таблице.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: не удалось добавить авторов. Попробуйте позже.");
        }

        model.addAttribute("authors", authorIsolationService.getAllAuthors());
        model.addAttribute("editingId", null);
        return "authors";
    }

    // Включение режима редактирования для автора
    @GetMapping("/edit/{id}")
    public String editAuthorForm(@PathVariable Long id, Model model) {
        AuthorDTO author = authorIsolationService.getAuthorById(id);
        if (author != null) {
            model.addAttribute("editingId", id);
            model.addAttribute("editingName", author.getName());
        } else {
            model.addAttribute("errorMessage", "Автор с таким ID не найден.");
        }
        model.addAttribute("authors", authorIsolationService.getAllAuthors());
        return "authors";
    }

    // Обновление данных автора
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @RequestParam("name") String name, Model model) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(name);
        try {
            authorIsolationService.updateAuthor(id, authorDTO);
            model.addAttribute("successMessage", "Данные автора успешно обновлены!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Ошибка: автор с таким ID не существует.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: не удалось обновить данные автора.");
        }

        model.addAttribute("authors", authorIsolationService.getAllAuthors());
        model.addAttribute("editingId", null);
        return "authors";
    }

    // Удаление автора
    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, Model model) {
        try {
            authorIsolationService.deleteAuthor(id);
            model.addAttribute("successMessage", "Автор успешно удален!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка: не удалось удалить автора.");
        }

        model.addAttribute("authors", authorIsolationService.getAllAuthors());
        model.addAttribute("editingId", null);
        return "authors";
    }

}
