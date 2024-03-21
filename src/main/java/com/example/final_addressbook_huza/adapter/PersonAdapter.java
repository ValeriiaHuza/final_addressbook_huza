package com.example.final_addressbook_huza.adapter;

import com.example.final_addressbook_huza.data.Person;
import lombok.*;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonAdapter {
    Integer id;
    Integer noteUser;
    String firstName;
    String lastName;
    String surname;
    String address;
    String email;
    String connection;
    String personDescription;
    String birthday;
    String lastModified;
    Set<JobAdapter> jobs;
    List<String> phonenumbers;
    Set<StudyAdapter> studies;
    Integer connectionId;


    public PersonAdapter(Optional<Person> person) {
        this.id = person.isPresent() ? person.get().getId() : 0;
        this.noteUser = person.isPresent() ? person.get().getUser().getId() : 0;
        this.firstName = person.isPresent() ? person.get().getFirstName() : "";
        String lastName1 = person.isPresent() ? person.get().getLastName() : "";

        if (lastName1 == null){
            lastName1 = "";
        }

        this.lastName = lastName1;
        String surname1 = person.isPresent() ? person.get().getSurname() : "";

        if(surname1 == null){
            surname1 = "";
        }
        this.surname = surname1;

        String address1 = person.isPresent() ? person.get().getAddress() : "";

        if(address1 == null){
            address1 = "";
        }
        this.address = address1;
        String email1 = person.isPresent() ? person.get().getEmail() : "";

        if(email1 == null){
            email1 = "";
        }
        this.email = email1;

        String connection1 = person.isPresent() ? person.get().getConnection().getTitle() : "";

        if(connection1 == null){
            connection1 = "";
        }
        this.connection = connection1;

        this.connectionId = person.isPresent() ? person.get().getConnection().getId() : 0;

        String personDescription1 = person.isPresent() ? person.get().getPersonDescription() : "";

        if(personDescription1 == null){
            personDescription1 = "";
        }

        //??
        this.personDescription = personDescription1;
        String birthday1 = "";

        if (person.isPresent() && person.get().getBirthday() != null) {
            birthday1 = person.get().getBirthday().toString();
        }

        //??
        this.birthday = birthday1;
        String lastModified1 = person.isPresent() ? String.valueOf(person.get().getLastModified()) : "";

        if(lastModified1 == null){
            lastModified1 = "";
        }

        this.lastModified = lastModified1;
        Set<JobAdapter> jobs = new HashSet<>();;
        person.ifPresent(value -> value.getJobs().forEach(job -> jobs.add(new JobAdapter(Optional.ofNullable(job)))));

        this.jobs = jobs;

        List<String> phonenumbers = new ArrayList<>();;
        person.ifPresent(value -> value.getPhonenumbers().forEach(phone -> phonenumbers.add(phone.getPhone())));

        this.phonenumbers = phonenumbers;

        Set<StudyAdapter> studies = new HashSet<>();;
        person.ifPresent(value -> value.getStudies().forEach(study -> studies.add(new StudyAdapter(Optional.ofNullable(study)))));

        this.studies = studies;
    }

}