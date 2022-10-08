package com.brovko.article.controller;

import com.brovko.article.dto.models.ArticleDTO;
import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.User;
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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;
    private Article article;
    private String json;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        Category category = new Category(1L, "Science", "Articles related to science and technologies",
                                    null);
//        article = new Article(1L, "Interesting article", false,
//                "Very interesting article about smth important", Arrays.asList(1L), User.builder().user_id(1L).build());

        ArticleDTO articleDTO = new ArticleDTO("Interesting article", false, "Very interesting article",
                Arrays.asList(1L), "Ruha");


        MvcResult result = mockMvc.perform(post("http://localhost:8089/api/login?username=Ruha&password=1234"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        token = JsonPath.parse(content).read("$['access_token']");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        json = mapper.writeValueAsString(articleDTO);
    }

    @SneakyThrows
    @Test
    void save_article() {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("http://localhost:8089/api/articles/1")
                                .header("authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))

                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$['name']");
        Assertions.assertThat(actualName).isEqualTo("Interesting article");
    }

    @SneakyThrows
    @Test
    void get_article_by_id() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8089/api/articles/1")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:8089/api/articles/1")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String contentFree = result.getResponse().getContentAsString();
        String freeName = JsonPath.parse(contentFree).read("$['name']");
        Assertions.assertThat(freeName).isEqualTo("Interesting article");



    }
}