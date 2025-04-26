package com.i2303.coursework.transactions.CourseworkTransactions.service;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.AuthorDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Author;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorIsolationService {

    private final AuthorRepository authorRepository;

    public AuthorIsolationService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Получение всех авторов
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toList());
    }

    // Уровень изоляции SERIALIZABLE для добавления авторов
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addMultipleAuthors(List<AuthorDTO> authors) {
        for (AuthorDTO dto : authors) {
            if (dto.getName() == null || dto.getName().isBlank()) {
                throw new IllegalArgumentException("Имя автора не может быть пустым");
            }
            Author author = new Author();
            author.setName(dto.getName());
            authorRepository.save(author);  // Это будет транзакционная операция
        }
    }

    // Уровень изоляции READ_COMMITTED для обновления авторов
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор с ID " + id + " не найден"));
        author.setName(authorDTO.getName());
        Author updatedAuthor = authorRepository.save(author);  // Сохраняем обновление
        return new AuthorDTO(updatedAuthor.getId(), updatedAuthor.getName());
    }

    // Уровень изоляции READ_UNCOMMITTED для удаления авторов
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Автор с ID " + id + " не найден");
        }
        authorRepository.deleteById(id);  // Удаляем автора
    }

    // Добавляем метод для получения автора по ID
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор с ID " + id + " не найден"));
        return new AuthorDTO(author.getId(), author.getName());
    }

}
