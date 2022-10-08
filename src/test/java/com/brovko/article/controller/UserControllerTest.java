package com.brovko.article.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;
    private User user;


    @SneakyThrows
    @BeforeEach
    void setUp() {
        MvcResult result = mockMvc.perform(post("http://localhost:8089/api/login?username=Ruha&password=1234"))
                .andReturn();

        user = new User("Andrew", "1234", "anbro2002@gmail.com",
                "+380859427462",
                LocalDateTime.of(2002, Month.JUNE, 27, 18, 30), 20, "Ukraine", "Odesa",
                "5959599593995393", "Brovko",
                "Ruha", new ArrayList<>());

        String content = result.getResponse().getContentAsString();
        token = JsonPath.parse(content).read("$['access_token']");
    }

    @SneakyThrows
    @Test
    void save_user() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String json = mapper.writeValueAsString(user);
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("http://localhost:8089/api/users")
                                .header("authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))

                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$['firstName']");

        Assertions.assertThat(actualName).isEqualTo(user.getFirstName());

    }


    @SneakyThrows
    @Test
    void get_all_users() {

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("http://localhost:8089/api/users")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$[0]['firstName']");
        Assertions.assertThat(actualName).isEqualTo("Andrew");
    }

    @SneakyThrows
    @Test
    void delete_user_by_id() {
        MvcResult posResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("http://localhost:8089/api/users/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String posContent = posResult.getResponse().getContentAsString();
        Assertions.assertThat(posContent).isEqualTo("Successfully deleted!!!");



        MvcResult negResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("http://localhost:8089/api/users/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String negContent = negResult.getResponse().getContentAsString();
        Assertions.assertThat(negContent).isEqualTo("Could not delete user with id 1");
    }

    @SneakyThrows
    @Test
    void get_user_by_id(){
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("http://localhost:8089/api/users/1")
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        String content = result.getResponse().getContentAsString();
        String actualName = JsonPath.parse(content).read("$['firstName']");
        Assertions.assertThat(actualName).isEqualTo("Andrew");
    }


}