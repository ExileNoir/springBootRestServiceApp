package com.infernalwhaler.springbootrestservice.repository;

import com.infernalwhaler.springbootrestservice.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 29/09/2021
 */

@Repository
public class LibraryRepositoryImpl implements ILibraryRepositoryCustom {

    @Autowired
    private ILibraryRepository repository;

    @Override
    public List<Library> findAllByAuthor(final String authorName) {
        final List<Library> booksWithSearchedAuthor = repository.findAll()
                .stream()
                .filter(library -> Objects.equals(library.getAuthor(), authorName))
                .collect(Collectors.toList());

        return booksWithSearchedAuthor;
    }
}
