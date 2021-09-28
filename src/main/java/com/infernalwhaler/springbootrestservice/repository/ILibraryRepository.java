package com.infernalwhaler.springbootrestservice.repository;

import com.infernalwhaler.springbootrestservice.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

public interface ILibraryRepository extends JpaRepository<Library ,String> {

}
