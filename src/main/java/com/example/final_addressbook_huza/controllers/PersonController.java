package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.adapter.PersonAdapter;
import com.example.final_addressbook_huza.data.*;
import com.example.final_addressbook_huza.json.PersonData;
import com.example.final_addressbook_huza.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
    private JobService jobService;
    private EducationService educationService;

    // ?page=2&sort=name&order=asc
    @GetMapping("/user/{id}")
    public String findAllPersonsForUser(@PathVariable(name = "id") int userId, Model model,
                                        @RequestParam(defaultValue = "1", name = "page") int page,
                                        @RequestParam(defaultValue = "id", name = "sort") String sortField,
                                        @RequestParam(defaultValue = "asc", name = "order") String sortOrder,
                                        @RequestParam(defaultValue = "") List<Integer> connection,
                                        @Param("input") String input) {

        NoteUser user = noteUserService.getUserById(userId).orElse(new NoteUser());

        Page<Person> pagePerson = personService.getPersonsByUser(userId, page, sortField, sortOrder, input, connection);
        List<Person> personList = pagePerson.getContent();

        List<PersonAdapter> newList = new ArrayList<>();

        for (Person person : personList) {
            newList.add(new PersonAdapter(Optional.ofNullable(person)));
        }

        List<Person> birthdays = personService.getPersonsWithBirthdayToday(userId);

        model.addAttribute("input", input);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("reverseSortDir", sortOrder.equals("asc") ? "desc" : "asc");
        model.addAttribute("persons", newList);
        model.addAttribute("user", user);
        model.addAttribute("birthday", birthdays);
        model.addAttribute("currentPage", pagePerson.getNumber() + 1);
        model.addAttribute("totalItems", pagePerson.getTotalElements());
        model.addAttribute("totalPages", pagePerson.getTotalPages() == 0 ? 1 : pagePerson.getTotalPages());
        model.addAttribute("connections", connectionService.getConnections());
        model.addAttribute("connectionFilter", connection);

        return "notebook";
    }

    @GetMapping("/person/{id}")
    public String getPersonById(@PathVariable(name = "id") int userId, Model model) {
        Optional<Person> person = personService.getPersonById(userId);
        model.addAttribute("person", new PersonAdapter(person));
        return "person_info";
    }

    @GetMapping("/add/{id}")
    public String redirectToForm(@PathVariable(name = "id") int userId, Model model) {

        model.addAttribute("user", noteUserService.getUserById(userId).orElse(new NoteUser()));
        model.addAttribute("person", new PersonAdapter());
        model.addAttribute("connections", connectionService.getConnections());

        return "add_person";
    }

    @PostMapping("/save/{user_id}")
    public String savePerson(@PathVariable(name = "user_id") int userId, @RequestParam(name = "datajson") String jsonData, PersonAdapter person) {

        Person newPerson = createPerson(userId, person);

        List<Phonenumber> phoneNumbersList = new ArrayList<>();
        List<Job> jobList = new ArrayList<>();
        List<Education> educationList = new ArrayList<>();

        if (!jsonData.isEmpty()) {
            extractDataFromJson(jsonData, phoneNumbersList, jobList, educationList);
        }

        Person personCreated = personService.addNewPerson(newPerson);
        phoneNumbersList.forEach(phone -> {
            phone.setPerson(personCreated);
            phoneNumberService.addNewPhoneNumber(phone);
        });

        jobList.forEach(job -> {
            job.setPerson(personCreated);
            jobService.addNewJob(job);
        });

        educationList.forEach(education -> {
            education.setPerson(personCreated);
            educationService.addNewEducation(education);
        });

        return "redirect:/notebook/user/" + userId;
    }

    @PostMapping("/edit")
    public String editPerson(PersonAdapter person) {
        Person newPerson = createPerson(person.getNoteUser(), person);
        Person personCreated = personService.addNewPerson(newPerson);

        return "redirect:/notebook/edit/" + personCreated.getId();
    }

    private void extractDataFromJson(String jsonData, List<Phonenumber> phoneNumbersList, List<Job> jobList, List<Education> educationList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PersonData data = objectMapper.readValue(jsonData, PersonData.class);

            data.getPhoneNumbers().stream()
                    .filter(number -> !number.trim().isEmpty())
                    .map(number -> {
                        Phonenumber phone = new Phonenumber();
                        phone.setPhone(number);
                        return phone;
                    })
                    .forEach(phoneNumbersList::add);


            data.getEducations().stream()
                    .filter(educationJson -> !educationJson.getEducationPlace().trim().isEmpty() || !educationJson.getSpecialization().trim().isEmpty())
                    .forEach(educationJson -> {
                        Education education = new Education();
                        education.setEducationPlace(educationJson.getEducationPlace());
                        education.setSpecialization(educationJson.getSpecialization());
                        educationList.add(education);
                    });

            data.getJobs().stream()
                    .filter(jsonJobnumber -> !jsonJobnumber.getJobPlace().isEmpty() || !jsonJobnumber.getJobVacancy().isEmpty())
                    .forEach(jsonJobnumber -> {
                        Job job = new Job();
                        job.setJobPlace(jsonJobnumber.getJobPlace());
                        job.setVacancy(jsonJobnumber.getJobVacancy());
                        jobList.add(job);
                    });


        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not parse json data: " + jsonData, e);
        }
    }

    private Person createPerson(int userId, PersonAdapter person) {
        Person newPerson = new Person();

        newPerson.setUser(noteUserService.getUserById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
        newPerson.setFirstName(person.getFirstName());
        if (person.getLastName() != null && !person.getLastName().isEmpty()) {
            newPerson.setLastName(person.getLastName());
        }

        if (person.getId() != null) {
            newPerson.setId(person.getId());
        }

        if (person.getSurname() != null && !person.getSurname().isEmpty()) {
            newPerson.setSurname(person.getSurname());
        }

        if (person.getAddress() != null && !person.getAddress().isEmpty()) {
            newPerson.setAddress(person.getAddress());
        }

        if (person.getEmail() != null && !person.getEmail().isEmpty()) {
            newPerson.setEmail(person.getEmail());
        }

        if (person.getPersonDescription() != null && !person.getPersonDescription().isEmpty()) {
            newPerson.setPersonDescription(person.getPersonDescription());
        }

        if (person.getBirthday() != null && !person.getBirthday().isEmpty()) {
            newPerson.setBirthday(LocalDate.parse(person.getBirthday()));
        }

        if (person.getConnectionId() != null && person.getConnectionId() != 0) {
            newPerson.setConnection(connectionService.getConnectionById(person.getConnectionId()).orElseGet(Connection::new));
        }

        return newPerson;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "person_id") int id, @RequestParam(name = "user_id") int userId) {
        personService.deletePerson(id);
        return "redirect:/notebook/user/" + userId;
    }

    @GetMapping("/edit/{person_id}")
    public String edit(@PathVariable(name = "person_id") int id, Model model) {
        Optional<Person> person = personService.getPersonById(id);

        model.addAttribute("user", person.orElseGet(Person::new).getUser());
        model.addAttribute("connections", connectionService.getConnections());
        model.addAttribute("person", new PersonAdapter(person));
        model.addAttribute("phones", phoneNumberService.getPhonesByUserId(id));
        model.addAttribute("educations", educationService.getEducationsByUserId(id));
        model.addAttribute("jobs",  jobService.getJobsByUserId(id));

        model.addAttribute("phone", new Phonenumber());
        model.addAttribute("education", new Education());
        model.addAttribute("job", new Job());

        return "edit_person";
    }


}
