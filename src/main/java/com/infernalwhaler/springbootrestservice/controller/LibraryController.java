package com.infernalwhaler.springbootrestservice.controller;

import com.infernalwhaler.springbootrestservice.model.AddBookResponse;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import com.infernalwhaler.springbootrestservice.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LibraryController {

    @Autowired
    private ILibraryRepository repository;
    @Autowired
    private LibraryService service;
    @Autowired
    private AddBookResponse bookResponse;

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @PostMapping("/addBook")
    public ResponseEntity<AddBookResponse> addBookImpl(@RequestBody final Library library) {
        final String setBookId = service.buildId(library.getIsbn(), library.getAisle());
        bookResponse.setBookId(setBookId);

        if (service.checkBookAlreadyExists(setBookId)) {
            logger.info("Book exists so skipping creation of book");
            bookResponse.setMessage("Book already exists");
            return new ResponseEntity<>(bookResponse, HttpStatus.ACCEPTED);
        }

        logger.info("Book does not exist, so creating one Book");
        library.setId(setBookId);
        repository.save(library);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("unique", setBookId);

        bookResponse.setMessage("Succes Book is added");

        return new ResponseEntity<>(bookResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/findBookById/{id}")
    public ResponseEntity<Library> findBookById(@PathVariable(value = "id") final String id) {
        return repository.findById(id)
                .map(library -> new ResponseEntity<>(library, HttpStatus.FOUND))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findBook/{author}")
    public ResponseEntity<List<Library>> findBookByAuthor(@PathVariable("author") final String author) {
        final List<Library> allByAuthor = repository.findAllByAuthor(author);
        return new ResponseEntity<>(allByAuthor, HttpStatus.FOUND);
    }

    @GetMapping("/findBooks")
    public ResponseEntity<List<Library>> findBooks() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PutMapping("updateBook/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable("id") final String id, @RequestBody final Library library) {
        final Library libraryToUpdate = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        libraryToUpdate.setAisle(library.getAisle());
        libraryToUpdate.setBookName(library.getBookName());
        libraryToUpdate.setAuthor(library.getAuthor());

        repository.save(libraryToUpdate);

        return new ResponseEntity<>(libraryToUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<AddBookResponse> deleteBook(@RequestBody final Library library) {
        final String id = library.getId();
        final Library bookToDelete = repository.findById(library.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(bookToDelete);
        bookResponse.setBookId(id);
        bookResponse.setMessage("Book is Deleted");
        logger.info("Book is deleted");

        return new ResponseEntity<>(bookResponse, HttpStatus.ACCEPTED);
    }
}
