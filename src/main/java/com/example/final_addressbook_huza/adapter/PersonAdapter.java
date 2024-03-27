package com.example.final_addressbook_huza.adapter;

import com.example.final_addressbook_huza.data.Education;
import com.example.final_addressbook_huza.data.Job;
import com.example.final_addressbook_huza.data.Person;
import com.example.final_addressbook_huza.data.Phonenumber;
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
    Set<Job> jobs;
    List<String> phonenumbers;
    Set<Education> educations;
    Integer connectionId;


    public PersonAdapter(Optional<Person> person) {

        Person get = person.orElseGet(Person::new);

        this.id = get.getId();
        this.noteUser = get.getUser().getId();
        this.firstName = get.getFirstName();
        String lastName1 = get.getLastName();

        if (lastName1 == null){
            lastName1 = "";
        }

        this.lastName = lastName1;
        String surname1 = get.getSurname();

        if(surname1 == null){
            surname1 = "";
        }
        this.surname = surname1;

        String address1 = get.getAddress();

        if(address1 == null){
            address1 = "";
        }
        this.address = address1;
        String email1 = get.getEmail();

        if(email1 == null){
            email1 = "";
        }
        this.email = email1;

        String connection1 = get.getConnection().getTitle();

        if(connection1 == null){
            connection1 = "";
        }
        this.connection = connection1;

        this.connectionId = get.getConnection().getId();

        String personDescription1 = get.getPersonDescription();

        if(personDescription1 == null){
            personDescription1 = "";
        }

        this.personDescription = personDescription1;
        String birthday1 = "";

        if (get.getBirthday() != null) {
            birthday1 = get.getBirthday().toString();
        }

        //??
        this.birthday = birthday1;
        String lastModified1 = String.valueOf(get.getLastModified());

        if(lastModified1 == null){
            lastModified1 = "";
        }

        this.lastModified = lastModified1;
        Set<Job> jobs = new HashSet<>();

        for (Job job : get.getJobs()) {
            if (job.getJobPlace()==null){
                job.setJobPlace("");
            }
            if (job.getVacancy()==null){
                job.setVacancy("");
            }
            jobs.add(job);
        }

        this.jobs = jobs;

        List<String> phonenumbers = new ArrayList<>();

        for (Phonenumber phone : get.getPhonenumbers()) {
           phonenumbers.add(phone.getPhone());
        }

        this.phonenumbers = phonenumbers;

        Set<Education> educationA = new HashSet<>();

        for (Education education : get.getStudies()) {
            if (education.getEducationPlace()==null){
                education.setEducationPlace("");
            }
            if (education.getSpecialization()==null){
                education.setSpecialization("");
            }
           educationA.add(education);
        }
        this.educations = educationA;
    }

}