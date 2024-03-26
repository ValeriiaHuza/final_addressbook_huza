package com.example.final_addressbook_huza.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "birthday_wish")
public class BirthdayWish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "wish", nullable = false, length = Integer.MAX_VALUE)
    private String wish;

}