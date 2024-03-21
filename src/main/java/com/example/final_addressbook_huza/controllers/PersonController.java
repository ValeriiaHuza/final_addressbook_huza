package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.adapter.CombinedData;
import com.example.final_addressbook_huza.adapter.PersonAdapter;
import com.example.final_addressbook_huza.data.*;
import com.example.final_addressbook_huza.services.ConnectionService;
import com.example.final_addressbook_huza.services.NoteUserService;
import com.example.final_addressbook_huza.services.PersonService;
import com.example.final_addressbook_huza.services.PhoneNumberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/notebook")
public class PersonController {

    private PersonService personService;
    private NoteUserService noteUserService;
    private ConnectionService connectionService;
    private PhoneNumberService phoneNumberService;

    @GetMapping("/user/{id}")
    public String findAllPersonsForUser(@PathVariable(name = "id") int userId, Model model,
                                        @RequestParam(defaultValue = "1", name = "page") int page) {

        Optional<NoteUser> userOptional = noteUserService.getUserById(userId);
        NoteUser user = userOptional.get();

        List<Person> personList;

        Pageable paging = PageRequest.of(page - 1, 15);

        Page<Person> pagePerson;

        pagePerson = personService.getPersonsByUser(userId, paging);

//        } else {
//            pagePerson = tutorialRepository.findByTitleContainingIgnoreCase(keyword, paging);
//            model.addAttribute("keyword", keyword);
//        }

        personList = pagePerson.getContent();

        List<PersonAdapter> newList = new ArrayList<PersonAdapter>();

        for (Person person : personList){
            newList.add(new PersonAdapter(Optional.ofNullable(person)));
        }

        System.out.println(pagePerson.getNumber());

        model.addAttribute("persons", newList);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", pagePerson.getNumber() + 1);
        model.addAttribute("totalItems", pagePerson.getTotalElements());
        model.addAttribute("totalPages", pagePerson.getTotalPages());

        return "notebook";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable(name = "id") int userId, Model model) {

        PersonAdapter person = personService.getPersonById(userId);

        model.addAttribute("person", person);

        return "person_info";
    }

    @GetMapping("/add/{id}")
    public String redirectToForm(@PathVariable(name = "id") int userId, Model model) {

        Optional<NoteUser> userO = noteUserService.getUserById(userId);
        NoteUser user = userO.get();
        model.addAttribute("user", user);
        model.addAttribute("person", new PersonAdapter());

        List<Connection> connetionList = connectionService.getConnections();
        model.addAttribute("connections", connetionList);

        return "add_person";
    }

    @PostMapping("/save/{user_id}")
    public String savePerson(@PathVariable(name = "user_id") int userId, @RequestParam(name = "datajson") String phoneNumbersJson, PersonAdapter person) {

        System.out.println(person);

        Person newPerson = new Person();
        newPerson.setUser(noteUserService.getUserById(userId).get());
        newPerson.setFirstName(person.getFirstName());
        if(person.getLastName()!=null && !person.getLastName().isEmpty()) {
            newPerson.setLastName(person.getLastName());
        }

        if(person.getSurname()!=null && !person.getSurname().isEmpty()) {
            newPerson.setSurname(person.getSurname());
        }

        if(person.getAddress()!=null && !person.getAddress().isEmpty()) {
            newPerson.setAddress(person.getAddress());
        }

        if(person.getEmail()!=null && !person.getEmail().isEmpty()) {
            newPerson.setEmail(person.getEmail());
        }

        if(person.getPersonDescription()!=null && !person.getPersonDescription().isEmpty()) {
            newPerson.setPersonDescription(person.getPersonDescription());
        }

        if(person.getBirthday()!=null && !person.getBirthday().isEmpty()) {
            newPerson.setBirthday(LocalDate.parse(person.getBirthday()));
        }

        if(person.getConnectionId()!=null && person.getConnectionId()!=0) {
            newPerson.setConnection(connectionService.getConnectionById(person.getConnectionId()).get());
        }

        List<Phonenumber> phoneNumbersList = new ArrayList<>();

        if(!phoneNumbersJson.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> phoneNumbers = new ArrayList<>();
            try {
                phoneNumbers = objectMapper.readValue(phoneNumbersJson, new TypeReference<List<String>>(){});
            } catch (JsonProcessingException e) {
                System.out.println("Could not parse phone numbers");
            }

            for(String phone: phoneNumbers) {
                Phonenumber phoneNumber = new Phonenumber();
                phoneNumber.setPhone(phone);
                phoneNumbersList.add(phoneNumber);
            }

        }

        //jobs
        //studies

        Person personCreated =  personService.addNewPerson(newPerson);

        for(Phonenumber phone : phoneNumbersList ) {
            phone.setPerson(personCreated);
            phoneNumberService.addNewPhoneNumber(phone);
        }

        System.out.println(personCreated.toString());
        return "redirect:/notebook/user/"+userId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "person_id") int id,@RequestParam(name = "user_id") int userId) {
        personService.deletePerson(id);
        return "redirect:/notebook/user/"+userId;
    }


}
