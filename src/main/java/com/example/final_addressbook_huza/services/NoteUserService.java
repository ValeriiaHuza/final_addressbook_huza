package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.data.NoteUser;
import com.example.final_addressbook_huza.repositories.NoteUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NoteUserService {

    private NoteUserRepository noteUserRepository;

    public List<NoteUser> getUsers() {
        return noteUserRepository.findAll();
    }

    public Optional<NoteUser> getUserById(int userId) {
        return noteUserRepository.findById(userId);
    }

    public void deleteUser(int id) {
         noteUserRepository.deleteById(id);
    }

    public void addNewUser(NoteUser user) {
        noteUserRepository.save(user);
    }
}
