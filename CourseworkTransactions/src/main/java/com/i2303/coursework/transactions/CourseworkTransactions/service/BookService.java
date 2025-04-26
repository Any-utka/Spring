package com.i2303.coursework.transactions.CourseworkTransactions.service;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.BookDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Book;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Author;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Publisher;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Category;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.BookRepository;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.AuthorRepository;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.PublisherRepository;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {
        try {
            // Находим автора по ID
            Author author = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Автор с ID " + bookDTO.getAuthorId() + " не найден"));

            // Найти издателя по ID
            Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Издатель с ID " + bookDTO.getPublisherId() + " не найден"));

            // Получить категории книги
            Set<Category> categories = bookDTO.getCategoryIds().stream()
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Категория с ID " + id + " не найдена")))

                    .collect(Collectors.toSet());

            // Создаем объект книги
            Book book = new Book(bookDTO.getTitle(), author, publisher);
            book.setCategories(categories);

            // Сохраняем книгу
            Book savedBook = bookRepository.save(book);

            logger.info("Книга '{}' добавлена в базу данных", savedBook.getTitle());

            return new BookDTO(savedBook);  // Возвращаем DTO книги
        } catch (Exception e) {
            logger.error("Ошибка при добавлении книги", e);
            throw e;  // Пробрасываем исключение для отката транзакции
        }
    }

    // Другие методы
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        return new BookDTO(bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Книга не найдена")));
    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        // Находим книгу по ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с ID " + id + " не найдена"));

        // Обновляем поля книги
        book.setTitle(bookDTO.getTitle());

        // Находим автора и издателя по ID
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Автор с ID " + bookDTO.getAuthorId() + " не найден"));
        book.setAuthor(author);

        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Издатель с ID " + bookDTO.getPublisherId() + " не найден"));
        book.setPublisher(publisher);

        // Получаем категории книги
        Set<Category> categories = bookDTO.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Категория с ID " + categoryId + " не найдена")))
                .collect(Collectors.toSet());
        book.setCategories(categories);

        // Сохраняем изменения
        Book updatedBook = bookRepository.save(book);

        return new BookDTO(updatedBook);  // Возвращаем обновленную книгу
    }


    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
