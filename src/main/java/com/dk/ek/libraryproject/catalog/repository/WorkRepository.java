package com.dk.ek.libraryproject.catalog.repository;

import com.dk.ek.libraryproject.catalog.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work,Long> {
    List<Work> findByTitleContainingIgnoreCase(String title);
}
