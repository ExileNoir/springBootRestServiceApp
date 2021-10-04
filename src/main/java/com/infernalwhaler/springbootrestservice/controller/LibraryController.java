package com.infernalwhaler.springbootrestservice.controller;

import com.infernalwhaler.springbootrestservice.model.AddBookResponse;
import com.infernalwhaler.springbootrestservice.modelDto.LibraryDto;
import com.infernalwhaler.springbootrestservice.service.ILibraryService;
import com.infernalwhaler.springbootrestservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@RestController
@RequiredArgsConstructor
public class LibraryController {

    private final ILibraryService service;
    private final AddBookResponse bookResponse;

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @PostMapping("/addBook")
    public ResponseEntity<AddBookResponse> addBookImpl(@RequestBody final LibraryDto library) {
        final String setBookId = service.buildId(library.getIsbn(), library.getAisle());
        bookResponse.setBookId(setBookId);

        if (service.checkBookAlreadyExists(setBookId)) {
            logger.info("Book exists so skipping creation of book");
            bookResponse.setMessage("Book already exists");
            return new ResponseEntity<>(bookResponse, HttpStatus.ACCEPTED);
        }

        logger.info("Book does not exist, so creating one Book");
        library.setId(setBookId);
        service.save(library);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("unique", setBookId);

        bookResponse.setMessage("Succes Book is added");

        return new ResponseEntity<>(bookResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/findBookById/{id}")
    public ResponseEntity<LibraryDto> findBookById(@PathVariable(value = "id") final String id) {
        return service.findById(id)
                .map(library -> new ResponseEntity<>(library, HttpStatus.FOUND))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findBook/{author}")
    public ResponseEntity<List<LibraryDto>> findBookByAuthor(@PathVariable("author") final String author) {
        final List<LibraryDto> allByAuthor = service.findAllByAuthor(author);

        if (allByAuthor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allByAuthor, HttpStatus.FOUND);
    }

    @GetMapping("/findBooks")
    public ResponseEntity<List<LibraryDto>> findBooks() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping("updateBook/{id}")
    public ResponseEntity<LibraryDto> updateBook(@PathVariable("id") final String id, @RequestBody final LibraryDto library) {
        final LibraryDto libraryToUpdate = service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        libraryToUpdate.setAisle(library.getAisle());
        libraryToUpdate.setBookName(library.getBookName());
        libraryToUpdate.setAuthor(library.getAuthor());

        service.save(libraryToUpdate);

        return new ResponseEntity<>(libraryToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<AddBookResponse> deleteBook(@RequestBody final LibraryDto library) {
        final String id = library.getId();
        final LibraryDto bookToDelete = service.findById(library.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        service.delete(bookToDelete);
        bookResponse.setBookId(id);
        bookResponse.setMessage("Book is Deleted");
        logger.info("Book is deleted");

        return new ResponseEntity<>(bookResponse, HttpStatus.ACCEPTED);
    }
}
