package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.data.Phonenumber;
import com.example.final_addressbook_huza.services.PersonService;
import com.example.final_addressbook_huza.services.PhoneNumberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/phone")
public class PhoneController {

    private PhoneNumberService phoneNumberService;
    private PersonService personService;

    @PostMapping("/add")
    public String add(@RequestParam(name = "person_id") int personId, Phonenumber phone) {
        phone.setPerson(personService.getPersonById(personId).orElseThrow( () -> new IllegalArgumentException("Person not found")));
        phoneNumberService.addNewPhoneNumber(phone);
        return "redirect:/notebook/edit/"+personId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "phone_id") int id,@RequestParam(name = "person_id") int personId) {
        phoneNumberService.deletePhone(id);
        return "redirect:/notebook/edit/"+personId;
    }

}
