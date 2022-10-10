package com.brovko.article.controller;

import com.brovko.article.model.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String jobJson;
    private String token;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        MvcResult result = mockMvc.perform(post("http://localhost:8089/api/login?username=Ruha&password=1234"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        token = JsonPath.parse(content).read("$['access_token']");

        Job job = new Job(1L, "Software Engineer", new ArrayList<>());


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        jobJson = mapper.writeValueAsString(job);
    }

    @SneakyThrows
    @Test
    void save_job() {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("http://localhost:8089/api/jobs")
                                .header("authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jobJson))

                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertThat(content).isEqualTo(jobJson);
    }

    @SneakyThrows
    @Test
    void get_all_jobs() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8089/api/jobs")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("http://localhost:8089/api/jobs")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$[0]['jobName']");
        Assertions.assertThat(actualName).isEqualTo("Software Engineer");
    }

    @SneakyThrows
    @Test
    void get_job_by_id() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8089/api/jobs")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("http://localhost:8089/api/jobs/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$['jobName']");
        Assertions.assertThat(actualName).isEqualTo("Software Engineer");
    }

    @SneakyThrows
    @Test
    void delete_job_by_id() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8089/api/jobs")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobJson));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.delete("http://localhost:8089/api/jobs/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertThat(content).isEqualTo("Job deleted successfully!");
    }
}