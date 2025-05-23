package com.i2303.coursework.transactions.CourseworkTransactions.controller;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.BookDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Author;
import com.i2303.coursework.transactions.CourseworkTransactions.service.BookService;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.AuthorRepository;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.PublisherRepository;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookController(BookService bookService, AuthorRepository authorRepository, PublisherRepository publisherRepository, CategoryRepository categoryRepository) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("book", new BookDTO()); // Для формы добавления книги
        model.addAttribute("publishers", publisherRepository.findAll()); // Список издателей
        model.addAttribute("categories", categoryRepository.findAll()); // Список категорий
        model.addAttribute("authors", authorRepository.findAll()); // Список авторов
        return "books";
    }

    @PostMapping
    public String addBook(@ModelAttribute BookDTO bookDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("authors", authorRepository.findAll()); // Список авторов
            return "books"; // Возвращаем форму с ошибками
        }

        try {
            bookService.addBook(bookDTO);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при добавлении книги: " + e.getMessage());
            return "error";
        }

        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("book", new BookDTO()); // Очистка формы
        return "redirect:/books";
    }

    @GetMapping("/update/{id}")
    public String updateBookForm(@PathVariable Long id, Model model) {
        BookDTO bookDTO = bookService.getBookById(id);
        model.addAttribute ("id",id);
        model.addAttribute("book", bookDTO);
        model.addAttribute("publishers", publisherRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        return "books";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute BookDTO bookDTO,
                             BindingResult bindingResult,
                             Model model) {

        System.out.println (bookDTO.toString ());
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", bookDTO);
            model.addAttribute("authors", authorRepository.findAll());
            return "books";
        }

        try {
            bookService.updateBook(id, bookDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при обновлении книги: " + e.getMessage());
            return "error";
        }

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/books";
    }

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("error", "Произошла ошибка при обработке запроса.");
        return "error";
    }
}
