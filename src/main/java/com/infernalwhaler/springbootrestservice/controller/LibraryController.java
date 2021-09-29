package com.infernalwhaler.springbootrestservice.controller;

import com.infernalwhaler.springbootrestservice.model.AddBookResponse;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import com.infernalwhaler.springbootrestservice.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/addBook")
    public ResponseEntity<AddBookResponse> addBookImpl(@RequestBody final Library library) {
        final String setBookId = service.buildId(library.getIsbn(), library.getAisle());
        bookResponse.setBookId(setBookId);

        if (service.checkBookAlreadyExists(setBookId)) {
            bookResponse.setMessage("Book already exists");
            return new ResponseEntity<>(bookResponse, HttpStatus.ACCEPTED);
        }

        library.setId(setBookId);
        repository.save(library);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("unique", setBookId);

        bookResponse.setMessage("Succes Book is added");

        return new ResponseEntity<>(bookResponse, headers, HttpStatus.CREATED);
    }

    @GetMapping("/findBook/{id}")
    public ResponseEntity<Library> findBookById(@PathVariable(value = "id") final String id) {
        return repository.findById(id)
                .map(library -> new ResponseEntity<>(library, HttpStatus.FOUND))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
