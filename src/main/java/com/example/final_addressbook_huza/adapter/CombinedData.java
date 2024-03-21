package com.example.final_addressbook_huza.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CombinedData {
    private List<String> phoneNumbers;
    private List<Map<String, String>> jobs;

}
