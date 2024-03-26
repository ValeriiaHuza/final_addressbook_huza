package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("select j from Job j where j.person.id = ?1")
    List<Job> findByPersonId(int id);
}