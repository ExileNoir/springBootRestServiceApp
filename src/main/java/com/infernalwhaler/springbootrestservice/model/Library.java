package com.infernalwhaler.springbootrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

@Entity
@Table(name = "Storage")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "aisle")
    private int aisle;

    @Column(name = "author")
    private String author;

}
