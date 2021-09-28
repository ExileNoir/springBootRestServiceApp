package com.infernalwhaler.springbootrestservice;

import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRestServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestServiceApplication.class, args);
    }

    @Autowired
    ILibraryRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Library library = repository.findById("fdsefr343").get();
        System.out.println(library.getAuthor());

        repository.save(new Library("fdsefr000", "HelloWorld", "fdsefr0", 50, "Kitty"));

        repository.findAll().forEach((lib) -> System.out.println(lib.getAuthor()));
    }
}
