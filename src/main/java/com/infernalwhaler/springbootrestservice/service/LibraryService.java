package com.infernalwhaler.springbootrestservice.service;

import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {

    private final ILibraryRepository repository;


    public String buildId(final String isb, final int aisle) {
        if (isb.startsWith("Z")) {
            return "OLD" + isb + aisle;
        }
        return isb + aisle;
    }

    public boolean checkBookAlreadyExists(final String id) {
        return repository.findById(id)
                .isPresent();
    }

    @Override
    public Library save(final Library library) {
        return repository.save(library);
    }

    @Override
    public Optional<Library> findById(final String id) {
        return repository.findById(id);
    }

    @Override
    public List<Library> findAllByAuthor(final String authorName) {
        return repository.findAllByAuthor(authorName);
    }

    @Override
    public List<Library> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(final Library library) {
        repository.delete(library);
    }
}
