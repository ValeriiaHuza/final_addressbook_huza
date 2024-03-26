package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.data.Education;
import com.example.final_addressbook_huza.data.Phonenumber;
import com.example.final_addressbook_huza.services.EducationService;
import com.example.final_addressbook_huza.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/education")
public class EducationController {

    private EducationService educationService;
    private PersonService personService;

    @PostMapping("/add")
    public String add(@RequestParam(name = "person_id") int personId, Education education) {
        education.setPerson(personService.getPersonById(personId).orElseThrow( () -> new IllegalArgumentException("Person not found")));
        educationService.addNewEducation(education);
        return "redirect:/notebook/edit/"+personId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "education_id") int id,@RequestParam(name = "person_id") int personId) {
        educationService.deletEducation(id);
        return "redirect:/notebook/edit/"+personId;
    }


}
