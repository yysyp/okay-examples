package ps.demo.jpademo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ps.demo.jpademo.dto.BookDto;
import ps.demo.jpademo.error.BookNotFoundException;
import ps.demo.jpademo.error.BookUnSupportedFieldPatchException;
import ps.demo.jpademo.service.BookService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;


    // Find
    @GetMapping("/")
    List<BookDto> findAll() {
        return bookService.findAll();
    }

    // Save
    @PostMapping("/")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    BookDto newBookDto(@RequestBody BookDto newBookDto) {
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
        return bookService.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Save or update
    @PutMapping("/{id}")
    BookDto saveOrUpdate(@RequestBody BookDto newBookDto, @PathVariable Long id) {

        return bookService.findById(id)
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
    }

    // update author only
    @PatchMapping("/{id}")
    BookDto patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

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
        bookService.deleteById(id);
    }

}
