package com.dk.ek.libraryproject.common;

import com.dk.ek.libraryproject.catalog.model.Work;
import com.dk.ek.libraryproject.catalog.model.WorkType;
import com.dk.ek.libraryproject.catalog.repository.WorkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    private final WorkRepository workRepository;

    public InitData(WorkRepository workRepository) {this.workRepository = workRepository;}

    @Override
    public void run(String... args) {
        if(workRepository.count() == 0){
            workRepository.save(new Work(
                    "Building Java Programs",
                    WorkType.BOOK,
                    "An introductory to Java programming.",
                    "Stuart Reges, Marty Stepp",
                    "Programming, Java"));

            workRepository.save(new Work(
                    "Effective Java",
                    WorkType.BOOK,
                    "An introductory to Java programming.",
                    "Stuart Reges, Marty Stepp",
                    "Programming, Java"));

            workRepository.save(new Work(
                    "Microservices in Practice",
                    WorkType.ARTICLE,
                    "Architectural considerations & trade-offs.",
                    "Jane Doe",
                    "Software Architecture, Microservices"));

            workRepository.save(new Work(
                    "The Economist - Weekly",
                    WorkType.MAGAZINE,
                    "International news and analysis.",
                    "The Economist Editorial",
                    "News, Economics"));
        }
    }
}
