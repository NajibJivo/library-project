package com.dk.ek.libraryproject.catalog.repository;

import com.dk.ek.libraryproject.catalog.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
