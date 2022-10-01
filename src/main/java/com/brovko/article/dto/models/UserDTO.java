package com.brovko.article.dto.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String firstName;

    private String email;

    private String phone;

    private LocalDate birthDate;

    private int age;

    private String country;
    private String city;

    private String twitterLink;
    private String instagramLink;
    private String facebookLink;
    private String creditCardNumber;

    private boolean premium;

    private String lastName;
    private String userName;

    List<String> articleNames;

    Collection<String> roles;

    List<String> jobs;

    List<String> followingNames;

    List<String> followersNames;
}
