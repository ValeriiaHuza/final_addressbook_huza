package com.example.final_addressbook_huza.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonData {
    private List<String> phoneNumbers;
    private List<JSONJob> jobs;
    private List<JSONEducation> educations;
}

