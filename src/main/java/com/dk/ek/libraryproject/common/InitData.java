package com.dk.ek.libraryproject.common;

import com.dk.ek.libraryproject.catalog.model.Edition;
import com.dk.ek.libraryproject.catalog.model.Publisher;
import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.model.WorkType;
import com.dk.ek.libraryproject.catalog.repository.PublisherRepository;
import com.dk.ek.libraryproject.catalog.repository.WorkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final WorkRepository workRepository;
    private final PublisherRepository publisherRepository;

    public InitData(WorkRepository workRepository, PublisherRepository publisherRepository) {
        this.workRepository = workRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) {
        if (workRepository.count() == 0) {
            // --- Publishers ---
            Publisher addison = new Publisher();
            addison.setName("Addison-Wesley");
            addison.setAddress("Boston, MA");
            addison.setContactInfo("info@addison-wesley.com");
            publisherRepository.save(addison);

            Publisher prentice = new Publisher();
            prentice.setName("Prentice Hall");
            prentice.setAddress("Upper Saddle River, NJ");
            prentice.setContactInfo("info@prenticehall.com");
            publisherRepository.save(prentice);

            // --- Works ---
            Work effectiveJava = new Work(
                    "Effective Java",
                    WorkType.BOOK,
                    "A comprehensive guide to best practices in Java programming.",
                    "Joshua Bloch",
                    "Java, Programming"
            );

            Work cleanCode = new Work(
                    "Clean Code",
                    WorkType.BOOK,
                    "A handbook of agile software craftsmanship.",
                    "Robert C. Martin",
                    "Software Engineering, Programming"
            );

            // --- Editions ---
            Edition ej3 = new Edition();
            ej3.setEditionNumber("3rd Edition");
            ej3.setPublicationYear(2018);
            ej3.setFormatType("Hardcover");
            ej3.setPublisher(addison);
            effectiveJava.addEdition(ej3); // helper-metoden sætter work på edition

            Edition cc1 = new Edition();
            cc1.setEditionNumber("1st Edition");
            cc1.setPublicationYear(2008);
            cc1.setFormatType("Paperback");
            cc1.setPublisher(prentice);
            cleanCode.addEdition(cc1);

            // --- Save works (editions gemmes pga. cascade) ---
            workRepository.save(effectiveJava);
            workRepository.save(cleanCode);
        }
    }
}
