package com.infernalwhaler.springbootrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddBookResponse {

    private String message;
    private String bookId;
}
