package com.infernalwhaler.springbootrestservice.repository;

import com.infernalwhaler.springbootrestservice.model.Library;

import java.util.List;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 29/09/2021
 */

public interface ILibraryRepositoryCustom {

    List<Library> findAllByAuthor(final String authorName);
}
