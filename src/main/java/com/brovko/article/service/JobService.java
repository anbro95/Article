package com.brovko.article.service;

import com.brovko.article.model.Job;

import java.util.List;

public interface JobService {
    Job saveJob(Job job);
    Job getJobById(Long id);
    List<Job> getAllJobs();
    Job getJobByName(String name);
    String deleteJob(Long id);
    Job updateJob(Job job);
}
