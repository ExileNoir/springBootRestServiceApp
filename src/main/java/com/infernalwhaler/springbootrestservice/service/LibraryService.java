package com.infernalwhaler.springbootrestservice.service;

import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@Service
public class LibraryService {

    @Autowired
    private ILibraryRepository repository;

    public String buildId(final String isb, final int aisle) {
        return isb + aisle;
    }

    public boolean checkBookAlreadyExists(final String id) {
        return repository.findById(id)
                .isPresent();
    }
}
