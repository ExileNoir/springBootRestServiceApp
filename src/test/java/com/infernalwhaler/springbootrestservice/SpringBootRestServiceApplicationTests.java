package com.infernalwhaler.springbootrestservice;

import com.infernalwhaler.springbootrestservice.controller.LibraryController;
import com.infernalwhaler.springbootrestservice.model.AddBookResponse;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import com.infernalwhaler.springbootrestservice.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@RequiredArgsConstructor
class SpringBootRestServiceApplicationTests {

    private final LibraryController controller;
    @MockBean
    private ILibraryRepository repository;
    @MockBean
    private LibraryService service;

    @Test
    void contextLoads() {
    }

    @Test
    public void checkBuildIdLogic() {
        LibraryService service = new LibraryService(repository);
        final String id = service.buildId("ZMAN", 24);
        final String id1 = service.buildId("MAN", 24);

        Assertions.assertEquals("OLDZMAN24", id);
        Assertions.assertEquals("MAN24", id1);
    }

    @Test
    public void addBookAcceptedValidatedByHttpStatus() {
        //mock
        Mockito.when(service.buildId(buildLibrary().getIsbn(), buildLibrary().getAisle()))
                .thenReturn(buildLibrary().getId());

        Mockito.when(service.checkBookAlreadyExists(buildLibrary().getId()))
                .thenReturn(false);

        final ResponseEntity<AddBookResponse> response = controller.addBookImpl(buildLibrary());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addBookNotAcceptedValidatedByHttpStatus() {
        //mock
        Mockito.when(service.buildId(buildLibrary().getIsbn(), buildLibrary().getAisle()))
                .thenReturn(buildLibrary().getId());

        Mockito.when(service.checkBookAlreadyExists(buildLibrary().getId()))
                .thenReturn(true);

        final ResponseEntity<AddBookResponse> response = controller.addBookImpl(buildLibrary());

        Assertions.assertNotEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addBookAcceptedValidatedById() {
        //mock
        Mockito.when(service.buildId(buildLibrary().getIsbn(), buildLibrary().getAisle()))
                .thenReturn(buildLibrary().getId());

        Mockito.when(service.checkBookAlreadyExists(buildLibrary().getId()))
                .thenReturn(false);

        final ResponseEntity<AddBookResponse> response = controller.addBookImpl(buildLibrary());

        Assertions.assertEquals(buildLibrary().getId(), response.getBody().getBookId());
    }

    @Test
    public void  addBookAcceptedValidatedByMessage() {
        //mock
        Mockito.when(service.buildId(buildLibrary().getIsbn(), buildLibrary().getAisle()))
                .thenReturn(buildLibrary().getId());

        Mockito.when(service.checkBookAlreadyExists(buildLibrary().getId()))
                .thenReturn(false);

        final ResponseEntity<AddBookResponse> response = controller.addBookImpl(buildLibrary());

        Assertions.assertNotEquals("Book already exists", response.getBody().getMessage());

    }


    private Library buildLibrary() {
        return new Library("sfe322", "Spring", "sfe", 322, "ExileNoir");
    }

}
