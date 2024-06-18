package com.cs544.project.service;

import com.cs544.project.domain.Faculty;
import com.cs544.project.exception.CustomNotFoundException;
import com.cs544.project.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getFacultyById(int facultyId) throws CustomNotFoundException {
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new CustomNotFoundException(
                        "Error while creating  a course-offering. Faculty with id: " + facultyId
                                + " was not found in the database."));
    }
}
