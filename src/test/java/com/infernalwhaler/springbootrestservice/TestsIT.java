package com.infernalwhaler.springbootrestservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infernalwhaler.springbootrestservice.modelDto.LibraryDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 2/10/2021
 */

@SpringBootTest(classes = SpringBootRestServiceApplication.class)
@ActiveProfiles("test")
public class TestsIT {


    @SneakyThrows
    @Test
    public void shouldGetAuthorNameBooks() {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/findBook/someBody", String.class);

        JSONAssert.assertEquals(expectedBooks(), response.getBody(), false);
        JSONAssert.assertEquals("302", String.valueOf(response.getStatusCode().value()), false);
    }

    private String expectedBooks() {
        return "[\n" +
                "    {\n" +
                "        \"id\": \"001\",\n" +
                "        \"bookName\": \"Selenium\",\n" +
                "        \"isbn\": \"00\",\n" +
                "        \"aisle\": 1,\n" +
                "        \"author\": \"someBody\"\n" +
                "    }" +
                "]";
    }

    @Test
    public void shouldAddBook() {
        final TestRestTemplate restTemplate = new TestRestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<LibraryDto> request = new HttpEntity<>(buildLibrary(), headers);

        final ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addBook", request, String.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("{\"message\":\"Succes Book is added\",\"bookId\":\"" + buildLibrary().getId() + "\"}", response.getBody());
    }

    @Test
    public void shouldNotAddBook() {
        final TestRestTemplate restTemplate = new TestRestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<LibraryDto> request = new HttpEntity<>(buildLibrary(), headers);

        final ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addBook", request, String.class);

        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        Assertions.assertEquals("{\"message\":\"Book already exists\",\"bookId\":\"" + buildLibrary().getId() + "\"}", response.getBody());
    }

    private LibraryDto buildLibrary() {
        return new LibraryDto("0020", "End Of Fantastic4", "00", 20, "MF Doom");
    }

    @SneakyThrows
    private String mapperToJson(final LibraryDto library) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(library);
    }

}
