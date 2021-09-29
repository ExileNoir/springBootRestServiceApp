package com.infernalwhaler.springbootrestservice.repository;

import com.infernalwhaler.springbootrestservice.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author sDeseure
 * @project SpringBootRestService
 * @date 28/09/2021
 */

public interface ILibraryRepository extends JpaRepository<Library, String>, ILibraryRepositoryCustom {


}