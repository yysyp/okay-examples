package ps.demo.commupload.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.commupload.dto.BookDto;
import ps.demo.commupload.entity.Book;
import ps.demo.commupload.mapping.BookMapper;
import ps.demo.commupload.repository.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDto> findAll() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(e -> BookMapper.INSTANCE.toDto(e)).collect(Collectors.toList());
    }

    public Optional<BookDto> findById(Long id) {
        Optional<Book> bookDto = bookRepository.findById(id);
        return bookDto.stream().map(e -> BookMapper.INSTANCE.toDto(e)).findAny();

    }

    @Transactional
    public BookDto save(BookDto bookDto) {
        Book book1 = bookRepository.save(BookMapper.INSTANCE.toEntity(bookDto));
        return BookMapper.INSTANCE.toDto(book1);
    }

    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> findByPublishedDateAfter(LocalDate date) {
        List<Book> bookList = bookRepository.findByPublishedDateAfter(date);
        return bookList.stream().map(e -> BookMapper.INSTANCE.toDto(e)).collect(Collectors.toList());
    }
}