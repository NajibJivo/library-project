package com.dk.ek.libraryproject.catalog.dto;

public record EditionDto(Long id, String editionNumber, Integer year, String format, PublisherDto publisher) {}
