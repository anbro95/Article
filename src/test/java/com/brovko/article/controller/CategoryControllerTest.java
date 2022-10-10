package com.brovko.article.controller;

import com.brovko.article.model.Category;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String categoryJson;
    private String token;

    @SneakyThrows
    @BeforeEach
    void setUp(){
        MvcResult result = mockMvc.perform(post("http://localhost:8089/api/login?username=Ruha&password=1234"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        token = JsonPath.parse(content).read("$['access_token']");

        Category category = new Category(1L, "Science", "Articles related to science and technologies",
                null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        categoryJson = mapper.writeValueAsString(category);
    }

    @SneakyThrows
    @Test
    void save_category() {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("http://localhost:8089/api/categories")
                                .header("authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(categoryJson))

                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertThat(content).isEqualTo(categoryJson);
    }

    @SneakyThrows
    @Test
    void get_all_categories() {
         mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8089/api/categories")
                 .header("authorization", "Bearer " + token)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(categoryJson));



        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("http://localhost:8089/api/categories")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$[0]['name']");
        Assertions.assertThat(actualName).isEqualTo("Science");
    }

    @SneakyThrows
    @Test
    void get_category_by_id() {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8089/api/categories")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson));



        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("http://localhost:8089/api/categories/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$['name']");
        Assertions.assertThat(actualName).isEqualTo("Science");
    }

    @SneakyThrows
    @Test
    void delete_category_by_id() {
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8089/api/categories")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson));



        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.delete("http://localhost:8089/api/categories/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().is(200))
                .andReturn();

        String content = result.getResponse().getContentAsString();
       Assertions.assertThat(content).isEqualTo("Category deleted successfully!");
    }



}