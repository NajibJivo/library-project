package com.dk.ek.libraryproject.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkDto(
        Long id,
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be <= 200 chars")
        String title,

        String workType, // <-- skal være String, ikke WorkType
        @Size(max = 500, message = "Details must be <= 500 chars")
        String details,

        @NotBlank(message = "Author is required")
        String author,
        List<EditionDto> editions,
        String subjects) {}
