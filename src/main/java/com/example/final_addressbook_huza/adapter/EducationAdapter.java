package com.example.final_addressbook_huza.adapter;

import com.example.final_addressbook_huza.data.Education;
import lombok.Value;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link Education}
 */
@Value
public class EducationAdapter implements Serializable {
    Integer id;
    String educationPlace;
    String specialization;
    Integer person;

    public EducationAdapter(Optional<Education> study) {
        this.id = study.isPresent() ? study.get().getId() : 0;
        this.educationPlace = study.isPresent() ? study.get().getEducationPlace() : "";
        this.specialization = study.isPresent() ? study.get().getSpecialization() : "";
        this.person = study.map(Education::getId).orElse(0);
    }
}