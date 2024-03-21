package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}