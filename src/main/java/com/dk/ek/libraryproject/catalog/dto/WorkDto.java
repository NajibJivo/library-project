package com.dk.ek.libraryproject.catalog.dto;


import java.util.List;

public record WorkDto(
        Long id,
        String title,
        String workType, // <-- skal være String, ikke WorkType
        String details,

        String author,
        List<EditionDto> editions,
        String subjects) {}
