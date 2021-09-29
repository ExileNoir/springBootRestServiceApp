package com.infernalwhaler.springbootrestservice.service;

import com.infernalwhaler.springbootrestservice.model.AddBookResponse;
import com.infernalwhaler.springbootrestservice.model.Library;

import java.util.List;
import java.util.Set;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

public interface ILibraryService {

    String buildId(final String isb, final int aisle);

    boolean checkBookAlreadyExists(final String id);

    Set<Library> findAll();

    Library findById(final String id);

    AddBookResponse save(final Library library);

    void delete(final Library library);

    void deleteById(final String id);


    List<Library> findAllByAuthor(final String authorName);
}
