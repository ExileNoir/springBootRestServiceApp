package com.infernalwhaler.springbootrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 27/09/2021
 */

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {

    private Long id;
    private String content;

}
