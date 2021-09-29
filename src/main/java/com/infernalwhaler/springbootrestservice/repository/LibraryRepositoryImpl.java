package com.infernalwhaler.springbootrestservice.repository;

import com.infernalwhaler.springbootrestservice.model.Library;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 29/09/2021
 */

public class LibraryRepositoryImpl {

    @Autowired
    private ILibraryRepositoryCustom libraryRepository;


    public List<Library> findAllByAuthor(final String authorName){
        return null;
    }
}
