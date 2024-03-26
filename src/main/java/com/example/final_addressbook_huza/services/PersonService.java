package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.adapter.PersonAdapter;
import com.example.final_addressbook_huza.data.Person;
import com.example.final_addressbook_huza.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    public Page<Person> getPersonsByUser(int userId, int page, String sortField, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable paging = PageRequest.of(page - 1, 15, sort);

        Page<Person> persons = personRepository.findByUserId(userId, paging);

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

    public List<Person> getPersonsWithBirthdayToday(int userId) {
        LocalDate today = LocalDate.now();
        return personRepository.findByBirthdayAndUserId(userId, today.getMonthValue(), today.getDayOfMonth());
    }
}
