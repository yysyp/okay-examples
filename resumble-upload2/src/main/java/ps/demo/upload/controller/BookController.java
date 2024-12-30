package ps.demo.upload.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ps.demo.upload.dto.BookDto;
import ps.demo.upload.error.BookNotFoundException;
import ps.demo.upload.error.BookUnSupportedFieldPatchException;
import ps.demo.upload.service.BookService;
import io.micrometer.core.instrument.Counter;


import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final Counter counter;

    private final Timer durationTimer;

    private final MeterRegistry meterRegistry;

    private final BookService bookService;

    public BookController(MeterRegistry meterRegistry, BookService bookService) {

        this.meterRegistry = meterRegistry;
        this.bookService = bookService;

        counter = meterRegistry.counter("book.findAll.count");
        durationTimer = meterRegistry.timer("book.add,duration");
    }


    // Find
    @GetMapping("/")
    List<BookDto> findAll() {
        counter.increment();
        log.info("Get /");
        return bookService.findAll();
    }

    // Save
    @PostMapping("/")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    BookDto newBookDto(@RequestBody BookDto newBookDto) {
        log.info("New book");
        return bookService.save(newBookDto);
    }

    // Find
    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "BookDto not found",
                    content = @Content)})
    @GetMapping("/books/{id}")
    BookDto findOne(@PathVariable Long id) {
        log.info("Find book by ID {}", id);
        return bookService.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Save or update
    @PutMapping("/{id}")
    BookDto saveOrUpdate(@RequestBody BookDto newBookDto, @PathVariable Long id) {
        log.info("Save or update book by id: {}", id);
        Timer.Sample sample = Timer.start(meterRegistry);
        BookDto result = bookService.findById(id)
                .map(x -> {
                    x.setName(newBookDto.getName());
                    x.setAuthor(newBookDto.getAuthor());
                    x.setPrice(newBookDto.getPrice());
                    return bookService.save(x);
                })
                .orElseGet(() -> {
                    newBookDto.setId(id);
                    return bookService.save(newBookDto);
                });

        sample.stop(durationTimer);
        return result;
    }

    // update author only
    @PatchMapping("/{id}")
    BookDto patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
        log.info("Patch book by id:{}", id);
        return bookService.findById(id)
                .map(x -> {

                    String author = update.get("author");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);

                        // better create a custom method to update a value = :newValue where id = :id
                        return bookService.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new BookNotFoundException(id);
                });

    }

    @DeleteMapping("/{id}")
    void deleteBookDto(@PathVariable Long id) {
        log.info("Delete book by id:{}", id);
        bookService.deleteById(id);
    }

}
