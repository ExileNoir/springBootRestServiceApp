package com.infernalwhaler.springbootrestservice;

import com.infernalwhaler.springbootrestservice.controller.LibraryController;
import com.infernalwhaler.springbootrestservice.model.AddBookResponse;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import com.infernalwhaler.springbootrestservice.service.LibraryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class SpringBootRestServiceApplicationTests {

    @Autowired
    private LibraryController controller;
    @MockBean
    private ILibraryRepository repository;
    @MockBean
    private LibraryService service;

    @Test
    void contextLoads() {
    }

    @Test
    public void checkBuildIdLogic() {
        LibraryService service = new LibraryService();
        final String id = service.buildId("ZMAN", 24);
        final String id1 = service.buildId("MAN", 24);

        Assertions.assertEquals("OLDZMAN24", id);
        Assertions.assertEquals("MAN24", id1);
    }

    @Test
    public void addBookTest() {
        //mock
        Mockito.when(service.buildId(buildLibrary().getIsbn(), buildLibrary().getAisle()))
                .thenReturn(buildLibrary().getId());

        Mockito.when(service.checkBookAlreadyExists(buildLibrary().getId()))
                .thenReturn(false);

        final ResponseEntity<AddBookResponse> response = controller.addBookImpl(buildLibrary());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(buildLibrary().getId(), response.getBody().getBookId());
        Assertions.assertNotEquals("Book already exists", response.getBody().getMessage());

    }

    private Library buildLibrary() {
        return new Library("sfe322", "Spring", "sfe", 322, "ExileNoir");
    }

}
