package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Phonenumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhonenumberRepository extends JpaRepository<Phonenumber, Integer> {
}