package com.brovko.article.service;

import com.brovko.article.model.Job;
import com.brovko.article.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public Job saveJob(Job job) {
        log.info("Trying to save Job {}", job.getJob_id());
        return jobRepository.save(job);
    }

    public Job getJobById(Long id) {
        log.info("Trying to find Job with id {}", id);
        return jobRepository.findById(id).orElse(null);
    }

    public List<Job> getAllJobs() {
        log.info("Trying to get list of all Jobs");
        return jobRepository.findAll();
    }

    public Job getJobByName(String name) {
        log.info("Trying to find Job with name {}", name);
        return jobRepository.findJobByJobName(name).orElse(null);
    }

    public String deleteJob(Long id) {
        log.info("Trying to delete Job with id {}", id);
        try{
            jobRepository.deleteById(id);
        } catch(Exception e) {
            return "Job with id " + id + " not found";
        }

        return "Job deleted successfully!";
    }

    public Job updateJob(Job job){
        log.info("Trying to update Job with id {}", job.getJob_id());
        Job updatedJob = jobRepository.findById(job.getJob_id()).orElse(null);
        if(updatedJob == null) {
            return null;
        }

        updatedJob.setJobName(job.getJobName());

        return jobRepository.save(updatedJob);
    }
}
