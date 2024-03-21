package com.example.final_addressbook_huza.adapter;

import com.example.final_addressbook_huza.data.Job;
import com.example.final_addressbook_huza.data.Phonenumber;
import lombok.Value;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link com.example.final_addressbook_huza.data.Phonenumber}
 */
@Value
public class PhonenumberAdapter implements Serializable {
    Integer id;
    String phone;
    Integer person;

    public PhonenumberAdapter(Optional<Phonenumber> phone) {
        this.id = phone.isPresent() ? phone.get().getId() : 0;
        this.phone = phone.isPresent() ? phone.get().getPhone() : "";
        this.person = phone.map(Phonenumber::getId).orElse(0);
    }
}