package com.dk.ek.libraryproject.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "works")
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private WorkType workType;

    @Column(length = 2000)
    private String details;

    private String authors;
    private String subjects;

    public Work() {
        // Default constructor
    }

    // Constructor
    public Work(String title, WorkType workType, String details, String authors, String subjects) {
        this.title = title;
        this.workType = workType;
        this.details = details;
        this.authors = authors;
        this.subjects = subjects;
    }

    // Getters & Setters
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public WorkType getWorkType() {return workType;}

    public void setWorkType(WorkType workType) {this.workType = workType;}

    public String getDetails() {return details;}

    public void setDetails(String details) {this.details = details;}

    public String getAuthors() {return authors;}

    public void setAuthors(String authors) {this.authors = authors;}

    public String getSubjects() {return subjects;}

    public void setSubjects(String subjects) {this.subjects = subjects;}
}
