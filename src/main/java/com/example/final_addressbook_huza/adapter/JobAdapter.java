package com.example.final_addressbook_huza.adapter;

import com.example.final_addressbook_huza.data.Job;
import lombok.Value;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link com.example.final_addressbook_huza.data.Job}
 */
@Value
public class JobAdapter implements Serializable {
    Integer id;
    String jobPlace;
    String vacancy;
    Integer person;

    public JobAdapter(Optional<Job> job ) {
        this.id = job.isPresent() ? job.get().getId() : 0;
        this.jobPlace = job.isPresent() ? job.get().getJobPlace() : "";
        this.vacancy = job.isPresent() ? job.get().getVacancy() : "";
        this.person = job.map(Job::getId).orElse(0);
    }
}