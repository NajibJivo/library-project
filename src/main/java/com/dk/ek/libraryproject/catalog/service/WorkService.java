package com.dk.ek.libraryproject.catalog.service;

import com.dk.ek.libraryproject.catalog.dto.WorkDto;
import com.dk.ek.libraryproject.catalog.mapper.LibraryMapper;
import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.model.WorkType;
import com.dk.ek.libraryproject.catalog.repository.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkService {
    private final WorkRepository workRepo;
    private final LibraryMapper mapper;

    public WorkService(WorkRepository workRepo, LibraryMapper mapper) {
        this.workRepo = workRepo;
        this.mapper = mapper;
    }

    /** READ ALL -> DTO **/
    @Transactional(readOnly = true)
    public List<WorkDto> getAllWorks() {
        return workRepo.findAll().stream().map(mapper::toDto).toList();
    }

    /** READ BY ID -> DTO **/
    @Transactional(readOnly = true)
    public WorkDto getWorkById(Long id) {
        Work work = workRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Work not found: " + id));
        return mapper.toDto(work);
    }

    /** CREATE: DTO in -> DTO out **/
    public WorkDto createWork(WorkDto dto) {
        Work toSave = mapper.toEntity(dto);
        Work saved = workRepo.save(toSave);
        return mapper.toDto(saved);
    }

    /** UPDATE: DTO in -> DTO out **/
    public WorkDto updateWork(Long id, WorkDto dto) {
        Work existing = workRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Work not found: " + id));

        // opdater skalarfelter fra DTO
        existing.setTitle(dto.title());
        existing.setWorkType(dto.workType() == null ? null : WorkType.valueOf(dto.workType()));
        existing.setDetails(dto.details());
        existing.setAuthors(dto.author());
        existing.setSubjects(dto.subjects());

        // editions springer vi over i første omgang (kan tilføjes senere)
        Work saved = workRepo.save(existing);
        return mapper.toDto(saved);
    }

    /** DELETE **/
    public void deleteWorkById(Long id) {
        if (!workRepo.existsById(id)) throw new RuntimeException("Work not found: " + id);
        workRepo.deleteById(id);
    }

    /** SEARCH -> DTO LIST **/
    public List<WorkDto> searchWorksByTitle(String title) {
        return workRepo.findByTitleContainingIgnoreCase(title)
                .stream().map(mapper::toDto).toList();
    }
}
