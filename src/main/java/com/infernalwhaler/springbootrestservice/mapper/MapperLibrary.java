package com.infernalwhaler.springbootrestservice.mapper;

import com.infernalwhaler.springbootrestservice.model.Library;
import com.infernalwhaler.springbootrestservice.modelDto.LibraryDto;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 1/10/2021
 */

@Component
public class MapperLibrary {

    public Library mapToLibrary(final LibraryDto libraryDto) {
        return new Library(libraryDto.getId(), libraryDto.getBookName(), libraryDto.getIsbn(), libraryDto.getAisle(), libraryDto.getAuthor());
    }

    public LibraryDto mapToLibraryDto(final Library library) {
        return new LibraryDto(library.getId(), library.getBookName(), library.getIsbn(), library.getAisle(), library.getAuthor());
    }
}
