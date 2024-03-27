package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.json.WishRequest;
import com.example.final_addressbook_huza.services.BirthdayWishService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/sendWish")
    public String sendWishEmail(@RequestBody WishRequest wishRequest) {
        String wish = wishRequest.getWish();
        String email = wishRequest.getEmail();

        // Code to send the wish email to the provided email address

        System.out.println(wish);
        System.out.println(email);
        // Return a success message or any other response as needed
        return "Wish sent successfully to " + email;
    }
}
