package com.dk.ek.libraryproject.catalog.controller;


import com.dk.ek.libraryproject.catalog.dto.WorkDto;
import com.dk.ek.libraryproject.catalog.service.WorkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Hvorfor er dette bedre? Controlleren er tynd og “deklarativ”.
 Al fejlformatering sker ét sted (global handler).
 Du undgår at returnere null/tom body manuelt ved fejl. **/
@RestController
@RequestMapping("/api/works")
public class WorkController {
    private final WorkService workService;
    public WorkController(WorkService workService) { this.workService = workService; }

    @GetMapping
    public List<WorkDto> getAll() { return workService.getAllWorks(); }

    @GetMapping("/{id}")
    public WorkDto getById(@PathVariable Long id) {
        // Kaster NotFoundException hvis ikke findes -> GlobalExceptionHandler returnerer 404 ProblemDetail
        return workService.getWorkById(id);
    }

    @PostMapping
    public ResponseEntity<WorkDto> create(@Valid @RequestBody WorkDto dto) {
        var created = workService.createWork(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
        // Bonus (hvis du har id på DTO): return
        // ResponseEntity.created(URI.create("/api/works/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public WorkDto update(@PathVariable Long id,@Valid  @RequestBody WorkDto dto) {
        // Kaster NotFound/BadRequest -> håndteres globalt
        return workService.updateWork(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        // Kaster NotFound -> håndteres globalt
        workService.deleteWorkById(id);
    }

    @GetMapping("/search")
    public List<WorkDto> search(@RequestParam String title) {
        return workService.searchWorksByTitle(title);
    }
}
