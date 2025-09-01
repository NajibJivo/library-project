package com.dk.ek.libraryproject.catalog.repository;

import com.dk.ek.libraryproject.catalog.model.Edition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionRepository extends JpaRepository<Edition,Long> {

}
