package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.data.Education;
import com.example.final_addressbook_huza.data.Job;
import com.example.final_addressbook_huza.repositories.EducationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EducationService {
    private final EducationRepository educationRepository;

    public void addNewEducation(Education education) {
        educationRepository.save(education);
    }

    public List<Education> getEducationsByUserId(int id) {
        return educationRepository.findByPersonId(id);
    }

    public void deletEducation(int id) {
        educationRepository.deleteById(id);
    }
}
