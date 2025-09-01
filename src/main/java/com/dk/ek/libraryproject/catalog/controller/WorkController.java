package com.dk.ek.libraryproject.catalog.controller;


import com.dk.ek.libraryproject.catalog.dto.WorkDto;
import com.dk.ek.libraryproject.catalog.service.WorkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
public class WorkController {
    private final WorkService workService;

    public WorkController(WorkService workService) { this.workService = workService; }

    @GetMapping
    public List<WorkDto> getAll() { return workService.getAllWorks(); }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDto> getById(@PathVariable Long id) {
        try { return new ResponseEntity<>(workService.getWorkById(id),HttpStatus.OK);
        } catch (RuntimeException ex) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<WorkDto> create(@RequestBody WorkDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workService.createWork(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDto> update(@PathVariable Long id, @RequestBody WorkDto dto) {
        try { return ResponseEntity.ok(workService.updateWork(id, dto)); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try { workService.deleteWorkById(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }
    }

    @GetMapping("/search")
    public List<WorkDto> search(@RequestParam String title) {
        return workService.searchWorksByTitle(title);
    }
}
