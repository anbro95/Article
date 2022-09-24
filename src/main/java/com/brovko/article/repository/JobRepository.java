package com.brovko.article.repository;

import com.brovko.article.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findJobByJobName(String name);
}
