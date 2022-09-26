package com.brovko.article.controller;


import com.brovko.article.model.Job;
import com.brovko.article.service.JobService;
import com.brovko.article.service.JobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping("/jobs")
    public ResponseEntity<Job> saveJob(@RequestBody Job job) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/jobs").toUriString());
        return ResponseEntity.created(uri).body(jobService.saveJob(job));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok().body(job == null ? "Job with id " + id + " not found" : job);
    }

    @GetMapping("/jobs/name/{name}")
    public ResponseEntity<?> getJobByName(@PathVariable String name) {
        Job job = jobService.getJobByName(name);
        return ResponseEntity.ok().body(job == null ? "Job with name " + name + " not found" : job);
    }

    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable Long id) {
        return jobService.deleteJob(id);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok().body(jobService.getAllJobs());
    }

    @PutMapping("/jobs")
    public ResponseEntity<?> updateJob(@RequestBody Job job) {
        Job updatedJob = jobService.updateJob(job);
        return ResponseEntity.ok().body(updatedJob == null ? "Job with id " + job.getJob_id() + " not found" : updatedJob);
    }
}
