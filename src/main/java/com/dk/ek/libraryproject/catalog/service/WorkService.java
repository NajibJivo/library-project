package com.dk.ek.libraryproject.catalog.service;

import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.repository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {
    private WorkRepository workRepository;

    // Constructor injection
    public WorkService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    /** CREATE ( Save a new Work entity). **/
    public Work createWork(Work work) {
        return workRepository.save(work); // Save a new Work entity
    }

    /** READ LIST (Retrieve all Work entities). **/
    public List<Work> getAllWorks() {
        return workRepository.findAll();
    }

    /** READ BY ID ( Retrieve a Work entity by its ID). **/
    public Work getWorkById(Long id) {
        return workRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work not found with id " + id));
    }

    /** UPDATE (Update an existing Work entity). **/
    public Work updateWork(Long id, Work updated) {
        Work existing = getWorkById(id);
        existing.setTitle(updated.getTitle());
        existing.setWorkType(updated.getWorkType());
        existing.setDetails(updated.getDetails());
        existing.setAuthors(updated.getAuthors());
        existing.setSubjects(updated.getSubjects());
        return workRepository.save(existing);
    }

    /** DELETE (Delete a Work entity by its ID). **/
    public void deleteWorkById(Long id) {
        if(!workRepository.existsById(id)) {
            throw new RuntimeException("Work not found with id " + id);
        }
        workRepository.deleteById(id);
    }

    /** SEARCH (Search for Work entities by title). **/
    public List<Work> searchWorks(String title) {
       return workRepository.findByTitleContainingIgnoreCase(title);
    }
}
