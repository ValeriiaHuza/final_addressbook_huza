package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.adapter.PersonAdapter;
import com.example.final_addressbook_huza.data.Person;
import com.example.final_addressbook_huza.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    public Page<Person> getPersonsByUser(int userId, Pageable paging) {

        Page<Person> persons = personRepository.findByUserIdOrderByIdAsc(userId, paging);

        return persons;
    }

    public Optional<Person> getPersonById(int userId) {
        return personRepository.findById(userId);
    }

    public Person addNewPerson(Person newPerson) {
        return personRepository.save(newPerson);
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }
}
