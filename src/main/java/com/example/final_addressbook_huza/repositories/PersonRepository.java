package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    // Find persons by user ID with pagination
    Page<Person> findByUserId(Integer userId, Pageable paging);

    // Find persons with birthday today by user ID
    @Query("SELECT p FROM Person p WHERE p.user.id = ?1 AND EXTRACT(MONTH FROM p.birthday) = ?2 AND EXTRACT(DAY FROM p.birthday) = ?3")
    List<Person> findByBirthdayAndUserId(Integer userId, int monthValue, int dayOfMonth);

    // Find persons by name and user ID with pagination
    @Query("SELECT p FROM Person p WHERE CONCAT(COALESCE(p.firstName, ''), ' ', COALESCE(p.lastName, ''), ' ', COALESCE(p.surname, '')) ILIKE %?1%")
    Page<Person> findByInputAndUserId(String input, Integer userId, Pageable paging);

    // Find persons by connection IDs and user ID with pagination
    @Query("select p from Person p where p.connection.id IN ?1 AND p.user.id = ?2")
    Page<Person> findByConnectionIdInAndUserId(List<Integer> connectionIds, Integer userId,Pageable paging);

    // Search persons by name, connection, and user ID with pagination
    @Query("select p from Person p WHERE CONCAT(LOWER(COALESCE(p.firstName, '')), ' ', COALESCE(p.lastName, ''), ' ', COALESCE(p.surname, '')) ILIKE %?1% AND p.connection.id IN ?2 AND p.user.id = ?3")
    Page<Person> findByInputConnectionAndUserId(String input, List<Integer> connection, int userId, Pageable paging);
}