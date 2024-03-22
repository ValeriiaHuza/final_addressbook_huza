package com.example.final_addressbook_huza.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private NoteUser user;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "connection_id", nullable = false)
    private Connection connection;

    @Column(name = "person_description")
    private String personDescription;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "last_modified")
    private Instant lastModified;

    @OneToMany(mappedBy = "person")
    private Set<Job> jobs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<Phonenumber> phonenumbers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<Education> studies = new LinkedHashSet<>();

    @PrePersist
    @PreUpdate
    public void updateLastModified() {
        this.lastModified = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
    }

}