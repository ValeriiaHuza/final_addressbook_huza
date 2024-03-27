package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.data.NoteUser;
import com.example.final_addressbook_huza.services.NoteUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    public String redirectToForm(Model model) {
        model.addAttribute("user", new NoteUser());
        return "user_form";
    }

    @PostMapping("/save")
    public String save(NoteUser user) {
        noteUserService.addNewUser(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String redirectToEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", noteUserService.getUserById(id).orElseThrow(() -> new IllegalArgumentException("User not found")));
        return "user_form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam(name = "user_id") int id) {
        noteUserService.deleteUser(id);
        return "redirect:/";
    }
}
