package com.infernalwhaler.springbootrestservice.service;

import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.modelDto.LibraryDto;

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

    LibraryDto save(final LibraryDto library);

    Optional<LibraryDto> findById(final String id);

    List<LibraryDto> findAllByAuthor(final String authorName);

    List<LibraryDto> findAll();

    void delete(final LibraryDto library);
}
