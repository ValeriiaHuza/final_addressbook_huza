package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.NoteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteUserRepository extends JpaRepository<NoteUser, Integer> {
}