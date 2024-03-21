package com.example.final_addressbook_huza.controllers;


import com.example.final_addressbook_huza.services.NoteUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class UserController {

    private NoteUserService noteUserService;

    @GetMapping("/")
    public String findAllUsers(Model model) {

        model.addAttribute("users", noteUserService.getUsers());

        return "index";
    }
}
