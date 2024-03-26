package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.adapter.PersonAdapter;
import com.example.final_addressbook_huza.data.*;
import com.example.final_addressbook_huza.json.PersonData;
import com.example.final_addressbook_huza.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private JobService jobService;
    private EducationService educationService;

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

    @GetMapping("/person/{id}")
    public String getPersonById(@PathVariable(name = "id") int userId, Model model) {

        Optional<Person> person = personService.getPersonById(userId);
        PersonAdapter personAdapter = new PersonAdapter(person);
        model.addAttribute("person", personAdapter);
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
    public String savePerson(@PathVariable(name = "user_id") int userId, @RequestParam(name = "datajson") String jsonData, PersonAdapter person) {

        System.out.println(person);

        Person newPerson = createPerson(userId, person);

        List<Phonenumber> phoneNumbersList = new ArrayList<>();
        List<Job> jobList = new ArrayList<>();
        List<Education> educationList = new ArrayList<>();

        if (!jsonData.isEmpty()) {
            extractDataFromJson(jsonData, phoneNumbersList, jobList, educationList);
        }

        Person personCreated =  personService.addNewPerson(newPerson);

        for(Phonenumber phone : phoneNumbersList ) {
            phone.setPerson(personCreated);
            phoneNumberService.addNewPhoneNumber(phone);
        }

        for(Job job : jobList ) {
            job.setPerson(personCreated);
            jobService.addNewJob(job);
        }

        for (Education education : educationList){
            education.setPerson(personCreated);
            educationService.addNewEducation(education);
        }

        System.out.println(personCreated.toString());
        return "redirect:/notebook/user/"+userId;
    }

    @PostMapping("/edit/{user_id}")
    public String editPerson(@PathVariable(name = "user_id") int userId, PersonAdapter person) {

        System.out.println(person);

        Person newPerson = createPerson(userId, person);
        Person personCreated =  personService.addNewPerson(newPerson);

        System.out.println(personCreated.toString());
        return "redirect:/notebook/edit/"+userId;
    }

    private void extractDataFromJson(String jsonData, List<Phonenumber> phoneNumbersList, List<Job> jobList, List<Education> educationList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PersonData data = objectMapper.readValue(jsonData, PersonData.class);

            data.getPhoneNumbers().forEach(number -> {
                Phonenumber phone = new Phonenumber();
                if(!number.trim().isEmpty()) {
                    phone.setPhone(number);
                    phoneNumbersList.add(phone);
                }
            });

            data.getEducations().forEach(educationJson -> {
                Education education = new Education();
                if(!educationJson.getEducationPlace().trim().isEmpty()) {
                    education.setEducationPlace(educationJson.getEducationPlace());
                }
                if(!educationJson.getSpecialization().trim().isEmpty()) {
                    education.setSpecialization(educationJson.getSpecialization());
                }

                if(education.getEducationPlace() !=null || education.getSpecialization()!=null ){
                    educationList.add(education);
                }
            });

            data.getJobs().forEach(jsonJobnumber -> {
                Job job = new Job();
                if(!jsonJobnumber.getJobPlace().isEmpty()){
                    job.setJobPlace(jsonJobnumber.getJobPlace());
                }

                if(!jsonJobnumber.getJobVacancy().isEmpty()){
                    job.setVacancy(jsonJobnumber.getJobVacancy());
                }

                if(job.getJobPlace()!=null || job.getVacancy()!=null){
                    jobList.add(job);
                }
            });

        } catch (JsonProcessingException e) {
            // Handle the exception appropriately, e.g., logging or throwing a custom exception
            throw new IllegalArgumentException("Could not parse json data: " + jsonData, e);
        }
    }

    private Person createPerson(int userId, PersonAdapter person) {

        Person newPerson = new Person();

        newPerson.setUser(noteUserService.getUserById(userId).orElseThrow( () -> new IllegalArgumentException("User not found")));
        newPerson.setFirstName(person.getFirstName());
        if(person.getLastName()!=null && !person.getLastName().isEmpty()) {
            newPerson.setLastName(person.getLastName());
        }

        if(person.getId()!=null){
            newPerson.setId(person.getId());
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

        return newPerson;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "person_id") int id,@RequestParam(name = "user_id") int userId) {
        personService.deletePerson(id);
        return "redirect:/notebook/user/"+userId;
    }

    @GetMapping("/edit/{person_id}")
    public String edit(@PathVariable(name = "person_id") int id, Model model) {
        Optional<Person> person = personService.getPersonById(id);

        model.addAttribute("user", person.get().getUser());

        List<Connection> connetionList = connectionService.getConnections();
        model.addAttribute("connections", connetionList);
        model.addAttribute("person", new PersonAdapter(person));

        List<Phonenumber> phonenumberList = phoneNumberService.getPhonesByUserId(id);
        model.addAttribute("phones", phonenumberList);

        model.addAttribute("phone",new Phonenumber());
        model.addAttribute("education", new Education());
        model.addAttribute("job", new Job());

        List<Education> educationList = educationService.getEducationsByUserId(id);
        model.addAttribute("educations", educationList);

        List<Job> jobList = jobService.getJobsByUserId(id);
        model.addAttribute("jobs", jobList);
        return "edit_person";
    }


}
