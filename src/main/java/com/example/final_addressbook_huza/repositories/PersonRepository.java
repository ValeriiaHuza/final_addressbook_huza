package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Page<Person> findByUserId(Integer userId, Pageable paging);
}