package com.infernalwhaler.springbootrestservice.service;

import com.infernalwhaler.springbootrestservice.mapper.MapperLibrary;
import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.modelDto.LibraryDto;
import com.infernalwhaler.springbootrestservice.repository.ILibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@Service
@RequiredArgsConstructor
public class LibraryService implements ILibraryService {


    private final ILibraryRepository repository;
    private final MapperLibrary mapper;

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
    public LibraryDto save(final LibraryDto libraryDto) {
        final Library library = mapper.mapToLibrary(libraryDto);
        return mapper.mapToLibraryDto(repository.save(library));
    }

    @Override
    public Optional<LibraryDto> findById(final String id) {
        return repository.findById(id)
                .map(mapper::mapToLibraryDto);
    }

    @Override
    public List<LibraryDto> findAllByAuthor(final String authorName) {
        return repository.findByAuthor(authorName)
                .stream()
                .map(mapper::mapToLibraryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LibraryDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToLibraryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(final LibraryDto libraryDto) {
        repository.delete(mapper.mapToLibrary(libraryDto));
    }


}
