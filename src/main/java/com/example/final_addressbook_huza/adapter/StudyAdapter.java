package com.example.final_addressbook_huza.adapter;

import com.example.final_addressbook_huza.data.Phonenumber;
import com.example.final_addressbook_huza.data.Study;
import lombok.Value;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link com.example.final_addressbook_huza.data.Study}
 */
@Value
public class StudyAdapter implements Serializable {
    Integer id;
    String studyPlace;
    String specialization;
    Integer person;

    public StudyAdapter(Optional<Study> study) {
        this.id = study.isPresent() ? study.get().getId() : 0;
        this.studyPlace = study.isPresent() ? study.get().getStudyPlace() : "";
        this.specialization = study.isPresent() ? study.get().getSpecialization() : "";
        this.person = study.map(Study::getId).orElse(0);
    }
}