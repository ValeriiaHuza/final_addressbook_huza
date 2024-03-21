package com.example.final_addressbook_huza.adapter;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.final_addressbook_huza.data.NoteUser}
 */
@Value
@NoArgsConstructor(force = true)
public class NoteUserDto implements Serializable {
    String name;
    String login;
    String password;
    PersonAdapter person;
}