package com.example.final_addressbook_huza.controllers;

import com.example.final_addressbook_huza.data.Job;
import com.example.final_addressbook_huza.services.JobService;
import com.example.final_addressbook_huza.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/job")
public class JobController {
    private JobService jobService;
    private PersonService personService;

    @PostMapping("/add")
    public String add(@RequestParam(name = "person_id") int personId, Job job) {
        job.setPerson(personService.getPersonById(personId).orElseThrow(() -> new IllegalArgumentException("Person not found")));

        if (job.getJobPlace().trim().isEmpty()) {
            job.setJobPlace(null);
        }

        if (job.getVacancy().trim().isEmpty()) {
            job.setVacancy(null);
        }

        jobService.addNewJob(job);
        return "redirect:/notebook/edit/" + personId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "job_id") int id, @RequestParam(name = "person_id") int personId) {
        jobService.deleteJob(id);
        return "redirect:/notebook/edit/" + personId;
    }

}
