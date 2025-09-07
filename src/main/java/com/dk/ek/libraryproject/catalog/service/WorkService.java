package com.dk.ek.libraryproject.catalog.service;

import com.dk.ek.libraryproject.catalog.dto.WorkDto;
import com.dk.ek.libraryproject.catalog.mapper.LibraryMapper;
import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.model.WorkType;
import com.dk.ek.libraryproject.catalog.repository.WorkRepository;
import com.dk.ek.libraryproject.shared.BadRequestException;
import com.dk.ek.libraryproject.shared.NotFoundException;
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
                .orElseThrow(() ->  new NotFoundException("Work " + id + " not found"));
        return mapper.toDto(work);
    }

    /** CREATE: DTO in -> DTO out **/
    public WorkDto createWork(WorkDto dto) {
        // Precondition (giver 400 via  ApiException-handler, ikke 500)
        if (dto.title() == null || dto.title().isBlank()) {
            throw new BadRequestException("Title is required");
        }
        if (dto.author() == null || dto.author().isBlank()) {
            throw new BadRequestException("Author is required");
        }

        // Mapperen håndterer workType (case-insensitiv + BAD_REQUEST ved ugyldig)
        Work toSave = mapper.toEntity(dto);

        Work saved = workRepo.save(toSave);
        return mapper.toDto(saved);
    }

    /** UPDATE: DTO in -> DTO out **/
    public WorkDto updateWork(Long id, WorkDto dto) {
        Work existing = workRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Work " + id + " not found"));

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
        if (!workRepo.existsById(id)) new NotFoundException("Work " + id + " not found");
        workRepo.deleteById(id);
    }

    /** SEARCH -> DTO LIST **/
    public List<WorkDto> searchWorksByTitle(String title) {
        if(title == null || title.isBlank()) {
         throw new BadRequestException("Title must not be blank");
        }
        return workRepo.findByTitleContainingIgnoreCase(title)
                .stream().map(mapper::toDto).toList();
    }
}
