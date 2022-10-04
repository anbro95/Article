package com.brovko.article.service;

import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.Job;
import com.brovko.article.repository.JobRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {
    private JobService jobService;
    @Mock
    private JobRepository jobRepository;


    @BeforeEach
    void setUp() {
        jobService = spy(new JobServiceImpl(jobRepository));
    }

    @Test
    void save_job() {
        Long id = 1L;
        Job job = new Job(1L, "slesar", new ArrayList<>());
        doReturn(job).when(jobRepository).save(job);

        Assertions.assertThat(jobService.saveJob(job)).isEqualTo(job);
        verify(jobRepository).save(job);
    }

    @Test
    void get_job_by_id() {
        Long id = 1L;
        Job job = new Job(1L, "slesar", new ArrayList<>());

        doReturn(Optional.of(job)).when(jobRepository).findById(id);
        Assertions.assertThat(jobService.getJobById(id)).isEqualTo(job);
        verify(jobRepository).findById(id);
    }

    @Test
    void get_all_jobs() {
        List<Job> jobs = Arrays.asList(new Job(1L, "slesar", new ArrayList<>()));
        doReturn(jobs).when(jobRepository).findAll();
        Assertions.assertThat(jobService.getAllJobs()).isEqualTo(jobs);
        verify(jobRepository).findAll();
    }

    @Test
    void delete_job_by_id() {
        jobService.deleteJob(1L);
        verify(jobRepository).deleteById(1L);
    }
}