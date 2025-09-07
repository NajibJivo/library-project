package com.dk.ek.libraryproject.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "editions")
public class Edition{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "edition_number", length = 50)
    private String editionNumber;
    // var "year"
    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "format_type", nullable = false)
    private String formatType; // mere robust end 'format' i nogle DB’er

    @ManyToOne(optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;  // Unidirektionelt fra Edition -> Publisher

    @ManyToOne(optional = false)
    @JoinColumn(name = "work_id",  nullable = false)
    private Work work;


    public Edition() {
        // Default constructor
    }

    public Edition(String editionNumber, Integer publicationYear, String formatType) {
        this.editionNumber = editionNumber;
        this.publicationYear = publicationYear;
        this.formatType = formatType;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getEditionNumber() {return editionNumber;}

    public void setEditionNumber(String editionNumber) {this.editionNumber = editionNumber;}

    public Integer getPublicationYear() {return publicationYear;}

    public void setPublicationYear(Integer year) {this.publicationYear = year;}

    public String getFormatType() {return formatType;}

    public void setFormatType(String format) {this.formatType = format;}

    public Publisher getPublisher() {return publisher;}

    public void setPublisher(Publisher publisher) {this.publisher = publisher;}

    public Work getWork() {return work;}

    public void setWork(Work work) {this.work = work;}
}
