package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
}