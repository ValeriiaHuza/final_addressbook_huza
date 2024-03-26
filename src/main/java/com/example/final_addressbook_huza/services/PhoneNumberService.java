package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.data.Phonenumber;
import com.example.final_addressbook_huza.repositories.PhonenumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneNumberService {

    private final PhonenumberRepository phonenumberRepository;

    public void addNewPhoneNumber(Phonenumber phone) {
        phonenumberRepository.save(phone);
    }

    public List<Phonenumber> getPhonesByUserId(int id) {
        return phonenumberRepository.findByPersonId(id);
    }

    public void deletePhone(int id) {
        phonenumberRepository.deleteById(id);
    }
}
