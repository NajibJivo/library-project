package com.dk.ek.libraryproject.catalog.mapper;

import com.dk.ek.libraryproject.catalog.dto.EditionDto;
import com.dk.ek.libraryproject.catalog.dto.PublisherDto;
import com.dk.ek.libraryproject.catalog.dto.WorkDto;
import com.dk.ek.libraryproject.catalog.model.Edition;
import com.dk.ek.libraryproject.catalog.model.Publisher;
import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.model.WorkType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;


@Component
public class LibraryMapper {

    public WorkType toWorkType(String s) {
        if(s == null || s.isBlank()) {
            throw new RuntimeException("workType is required");
        }
        try{
            return WorkType.valueOf(s.trim().toUpperCase(Locale.ROOT));
        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException(String.format("Invalid workType: " + s));
        }
    }


    // valgfri: pæn titel-case i DTO ("BOOK" -> "Book")
    public String enumToTitleCase(WorkType wt) {
        if(wt == null) return null;
        String s = wt.name().toLowerCase();
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public WorkDto toDto(Work work) {
        List<EditionDto> eds = work.getEditions().stream().map(this::toDto).toList();

        return new WorkDto(
                work.getId(),
                work.getTitle(),
                enumToTitleCase(work.getWorkType()),
                work.getDetails(),
                work.getAuthors(),  // entity 'authors' -> dto 'author'
                eds,
                work.getSubjects()
        );
    }

    public PublisherDto toDto(Publisher p) {
        if(p == null) return null;
        return new PublisherDto(
          p.getId(),
          p.getName(),
          p.getAddress(),
          p.getContactInfo()
        );
    }

    public EditionDto toDto(Edition e) {
        int safeYear = (e.getPublicationYear() != null) ? e.getPublicationYear() : 0;
        return new EditionDto(
                e.getId(),
                e.getEditionNumber(),
                safeYear,
                e.getFormatType(),
                toDto(e.getPublisher())
        );
    }

   // DTO -> Entity (kun skalarfelter nu)
    public Work toEntity(WorkDto dto) {
        Work w = new Work();
        w.setId(dto.id()); // ok at være null ved create
        w.setTitle(dto.title());
        w.setWorkType(toWorkType(dto.workType()));
        w.setDetails(dto.details());
        w.setAuthors(dto.author());
        w.setSubjects(dto.subjects());
        return w;
    }
}
