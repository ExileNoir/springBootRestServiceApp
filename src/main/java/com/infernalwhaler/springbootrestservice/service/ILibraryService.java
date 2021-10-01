package com.infernalwhaler.springbootrestservice.service;

import com.infernalwhaler.springbootrestservice.model.Library;

import java.util.List;
import java.util.Optional;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

public interface ILibraryService {

    String buildId(final String isb, final int aisle);

    boolean checkBookAlreadyExists(final String id);

    Library save(final Library library);

    Optional<Library> findById(final String id);

    List<Library> findAllByAuthor(final String authorName);

    List<Library> findAll();

    void delete(final Library library);
}
