package com.i2303.coursework.transactions.CourseworkTransactions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/page")
public class PageController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        return "redirect:/authors";
    }

    @GetMapping("/books")
    public String books(Model model) {
        return "redirect:/books";
    }
    @GetMapping("/categories")
    public String categories(Model model) {
        return "redirect:/categories";
    }
}
