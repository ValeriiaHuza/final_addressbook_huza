package com.example.final_addressbook_huza.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "job_place")
    private String jobPlace;

    @Column(name = "vacancy")
    private String vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public String toString() {
        return "Job{" +
                "jobPlace='" + jobPlace + '\'' +
                ", vacancy='" + vacancy + '\'' +
                '}';
    }
}