package com.infernalwhaler.springbootrestservice.controller;

import com.infernalwhaler.springbootrestservice.model.Greeting;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 27/09/2021
 */

@RestController
public class GreetingController {

    @Autowired
    private Greeting greeting;
    private final AtomicLong counterId = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name") final String name) {
        greeting.setId(counterId.incrementAndGet());
        greeting.setContent("Hey I am Learning Spring Boot from " + name);
        return greeting;
    }


}
