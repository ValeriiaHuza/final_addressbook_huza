package com.example.final_addressbook_huza.repositories;

import com.example.final_addressbook_huza.data.BirthdayWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BirthdayWishRepository extends JpaRepository<BirthdayWish, Integer> {
    @Query(value = "SELECT wish FROM birthday_wish ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    String getRandomBirthdayWish();

}