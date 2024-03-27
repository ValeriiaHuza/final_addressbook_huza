package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.services.BirthdayWishService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BirthdayController {
    private BirthdayWishService birthdayWishService;

    @GetMapping("/getRandomWish")
    public String getRandomBirthdayWish() {
        // Fetch a random birthday wish from the service
        return birthdayWishService.getRandomBirthdayWish(); // Return only the wish string
    }
}
