package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Phonenumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhonenumberRepository extends JpaRepository<Phonenumber, Integer> {
    @Query("select p from Phonenumber p where p.person.id = ?1")
    List<Phonenumber> findByPersonId(Integer id);

}