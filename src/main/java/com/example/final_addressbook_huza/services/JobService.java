package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.data.Job;
import com.example.final_addressbook_huza.repositories.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public void addNewJob(Job job) {
        jobRepository.save(job);
    }

    public List<Job> getJobsByUserId(int id) {
        return jobRepository.findByPersonId(id);
    }

    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }
}
