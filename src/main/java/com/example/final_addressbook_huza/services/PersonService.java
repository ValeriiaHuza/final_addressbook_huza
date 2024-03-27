package com.example.final_addressbook_huza.services;

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
    private static final int PAGE_NUMBER = 15;
    private final PersonRepository personRepository;

    public Page<Person> getPersonsByUser(int userId, int page, String sortField, String sortOrder, String input, List<Integer> connection) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable paging = PageRequest.of(page - 1, PAGE_NUMBER, sort);

        if (input != null && !input.isEmpty()) {
             //input = input.toLowerCase();
            System.out.println(input);
            if (connection != null && !connection.isEmpty()) {
                return personRepository.findByInputConnectionAndUserId(input, connection, userId, paging);
            } else {
                return personRepository.findByInputAndUserId(input, userId, paging);
            }
        } else if (connection != null && !connection.isEmpty()) {
            return personRepository.findByConnectionIdInAndUserId(connection, userId, paging);
        } else {
            return personRepository.findByUserId(userId, paging);
        }
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
