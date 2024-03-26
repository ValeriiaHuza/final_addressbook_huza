package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.repositories.BirthdayWishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BirthdayWishService {

    private final BirthdayWishRepository birthdayWishRepository;

    public String getRandomBirthdayWish() {
        return birthdayWishRepository.getRandomBirthdayWish();
    }
}
