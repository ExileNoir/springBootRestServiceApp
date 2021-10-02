package com.infernalwhaler.springbootrestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.modelDto.LibraryDto;
import com.infernalwhaler.springbootrestservice.service.LibraryService;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 1/10/2021
 */
@SpringBootTest
@AutoConfigureMockMvc
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService service;

    @SneakyThrows
    @Test
    public void adBookControllerAccepted() {
        final LibraryDto lib = buildLibrary01();

        when(service.buildId(lib.getIsbn(), lib.getAisle()))
                .thenReturn(lib.getId());
        when(service.checkBookAlreadyExists(lib.getId()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/addBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(buildLibrary01())))                  // end of perform action
                .andExpect(MockMvcResultMatchers.status().isCreated())            // begin of result action
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Succes Book is added"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").value(lib.getId()))
                .andDo(print())                                                // console output to verify
                .andReturn()
                .getResponse();
    }

    @SneakyThrows
    @Test
    public void adBookControllerNotAccepted() {
        final LibraryDto lib = buildLibrary01();

        when(service.buildId(lib.getIsbn(), lib.getAisle()))
                .thenReturn(lib.getId());
        when(service.checkBookAlreadyExists(lib.getId()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/addBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(buildLibrary01())))                   // end of perform action
                .andExpect(MockMvcResultMatchers.status().isAccepted())            // begin of result action
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book already exists"))
                .andDo(print())                                                 // console output to verify
                .andReturn()
                .getResponse();
    }

    @SneakyThrows
    @Test
    public void findBookByAuthor() {
        when(service.findAllByAuthor(ArgumentMatchers.any()))
                .thenReturn(buildLibraries());

        mockMvc.perform(MockMvcRequestBuilders.get("/findBook/ExileNoir")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(3)))
                .andDo(print())                                                 // console output to verify
                .andReturn()
                .getResponse();
    }

    @SneakyThrows
    @Test
    public void findEmptyBookByAuthor() {
        when(service.findAllByAuthor(ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/findBook/ExileNoir")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print())                                                 // console output to verify
                .andReturn()
                .getResponse();
    }

    @SneakyThrows
    @Test
    public void updateBook() {
        when(service.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(buildLibrary01()));

        mockMvc.perform(MockMvcRequestBuilders.put("/updateBook/" + buildLibrary01().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateMapperToJson(buildLibrary01())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName")
                        .value("Fantastic4 and how I ended them"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author")
                        .value("Mighty MF DOOM"))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(print())
                .andReturn()
                .getResponse();
    }

    @SneakyThrows
    @Test
    public void deleteBook() {
        when(service.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(buildLibrary02()));
        Mockito.doNothing()
                .when(service)
                .delete(buildLibrary01());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteBook")
                .contentType(APPLICATION_JSON)
                .content("{\"id\": \""+buildLibrary01().getId()+"\"}"))
                .andExpect(content().string("{\"message\":\"Book is Deleted\",\"bookId\":\""+buildLibrary01().getId()+"\"}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(print())
                .andReturn()
                .getResponse();
    }

    private LibraryDto buildLibrary() {
        return new LibraryDto("sfe322", "Spring", "sfe", 322, "ExileNoir");
    }

    private LibraryDto buildLibrary01() {
        return new LibraryDto("0020", "End Of Fantastic4", "00", 20, "MF Doom");
    }

    private LibraryDto buildLibrary02() {
        return new LibraryDto("007", "Die Another Day", "00", 7, "James Bond");
    }

    private List<LibraryDto> buildLibraries() {
        return Stream.of(buildLibrary(), buildLibrary01(), buildLibrary02())
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private String mapperToJson(final LibraryDto library) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(library);
    }

    @SneakyThrows
    private String updateMapperToJson(final LibraryDto library) {
        final ObjectMapper mapper = new ObjectMapper();
        library.setAisle(200);
        library.setBookName("Fantastic4 and how I ended them");
        library.setAuthor("Mighty MF DOOM");
        return mapper.writeValueAsString(library);
    }
}