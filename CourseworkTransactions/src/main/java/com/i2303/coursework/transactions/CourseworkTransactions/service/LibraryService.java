package com.i2303.coursework.transactions.CourseworkTransactions.service;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.LibraryDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Library;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.LibraryRepository;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    // Получение всех библиотек
    public List<LibraryDTO> getAllLibraries() {
        List<Library> libraries = libraryRepository.findAll();
        //libraryRepository.
        return libraries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Преобразование библиотеки в DTO
    public LibraryDTO convertToDto(Library library) {
        return new LibraryDTO(
                library.getId(),
                library.getBooks().stream()
                        .map(book -> book.getId())
                        .collect(Collectors.toList())
        );
    }

    // Получение библиотеки по ID
    public Library getLibraryById(Long id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library not found"));
    }

    // Добавление библиотеки
    public Library saveLibrary(Library library) {
        return libraryRepository.save(library);
    }

    // Обновление библиотеки
    public Library updateLibrary(Long id, LibraryDTO libraryDTO) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library not found"));
        library.setBooks(libraryDTO.getBookIds().stream()
                .map(bookId -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found"))).collect(Collectors.toList()));
        return libraryRepository.save(library);
    }

    // Удаление библиотеки
    public void deleteLibraryById(Long id) {
        libraryRepository.deleteById(id);
    }
}
