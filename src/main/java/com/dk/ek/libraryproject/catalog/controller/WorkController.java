package com.dk.ek.libraryproject.catalog.controller;


import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.service.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/works")
public class WorkController {
    private WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    // GET /api/works
    @GetMapping
    public ResponseEntity <List<Work>> getAllWorks() {
        return ResponseEntity.ok(workService.getAllWorks());
    }

    // GET /api/works/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Work> getWorkById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(workService.getWorkById(id));
    }

    // POST /api/works
    @PostMapping
    public ResponseEntity<Work> createWork(@RequestBody Work work) {
        Work created = workService.createWork(work);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/works/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Work> updateWork(@PathVariable("id") Long id, @RequestBody Work work) {
        try{
            return ResponseEntity.ok(workService.updateWork(id, work));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // DELETE /api/works/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable("id") Long id) {
        try{ workService.deleteWorkById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET /api/works/search?title=...
    @GetMapping("/search")
    public ResponseEntity<List<Work>> searchByTitle(@RequestParam("title") String title) {
        return ResponseEntity.ok(workService.searchWorks(title));
    }
}
