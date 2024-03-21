package com.example.final_addressbook_huza.adapter;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.final_addressbook_huza.data.Connection}
 */
@Value
public class ConnectionDto implements Serializable {
    Integer id;
    String title;
}