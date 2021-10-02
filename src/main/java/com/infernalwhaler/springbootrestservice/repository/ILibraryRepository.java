package com.infernalwhaler.springbootrestservice.repository;

import com.infernalwhaler.springbootrestservice.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@Repository
public interface ILibraryRepository extends JpaRepository<Library, String> {

    List<Library> findByAuthor(final String authorName);

}
