package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select p from Person p where p.user.id = ?1 order by p.id")
    Page<Person> findByUserIdOrderByIdAsc(Integer userId, Pageable paging);

}