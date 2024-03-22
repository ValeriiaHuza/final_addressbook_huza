package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.data.Job;
import com.example.final_addressbook_huza.repositories.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;


    public void addNewJob(Job job) {
        jobRepository.save(job);
    }

}
