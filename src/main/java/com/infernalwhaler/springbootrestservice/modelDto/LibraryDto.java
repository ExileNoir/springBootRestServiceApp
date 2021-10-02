package com.infernalwhaler.springbootrestservice.modelDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 1/10/2021
 */

@Data
@AllArgsConstructor
public class LibraryDto {

    private String id;
    private String bookName;
    private String isbn;
    private int aisle;
    private String author;
}
