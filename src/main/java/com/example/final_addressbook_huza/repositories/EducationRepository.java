package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    @Query("select e from Education e where e.person.id = ?1")
    List<Education> findByPersonId(int id);
}