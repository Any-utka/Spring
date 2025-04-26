package com.i2303.coursework.transactions.CourseworkTransactions.controller;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.LibraryDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Library;
import com.i2303.coursework.transactions.CourseworkTransactions.service.LibraryService;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookRepository bookRepository;

    // Получение всех библиотек
    @GetMapping
    public List<LibraryDTO> getAllLibraries() {
        // Получаем список всех библиотек и преобразуем их в DTO
        return libraryService.getAllLibraries();
    }

    // Добавление библиотеки
    @PostMapping
    public LibraryDTO addLibrary(@RequestBody LibraryDTO libraryDTO) {
        // Преобразуем DTO в сущность Library
        Library library = new Library();
        library.setBooks(libraryDTO.getBookIds().stream()
                .map(bookId -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found")))
                .collect(Collectors.toList()));

        // Сохраняем библиотеку в базе данных
        Library savedLibrary = libraryService.saveLibrary(library);

        // Преобразуем сохраненную библиотеку в DTO и возвращаем
        return libraryService.convertToDto(savedLibrary);
    }

    // Получение библиотеки по ID
    @GetMapping("/{id}")
    public LibraryDTO getLibraryById(@PathVariable Long id) {
        // Получаем библиотеку через сервис
        Library library = libraryService.getLibraryById(id);

        // Преобразуем библиотеку в DTO и возвращаем
        return libraryService.convertToDto(library);
    }

    // Обновление библиотеки
    @PutMapping("/{id}")
    public LibraryDTO updateLibrary(@PathVariable Long id, @RequestBody LibraryDTO libraryDTO) {
        // Обновляем библиотеку через сервис
        Library library = libraryService.updateLibrary(id, libraryDTO);

        // Преобразуем обновленную библиотеку в DTO и возвращаем
        return libraryService.convertToDto(library);
    }

    // Удаление библиотеки
    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable Long id) {
        // Удаляем библиотеку через сервис
        libraryService.deleteLibraryById(id);
    }
}
