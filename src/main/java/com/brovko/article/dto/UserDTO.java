package com.brovko.article.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long user_id;
    private String firstName;
    private String password;
    private LocalDate createdAt;
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

    private String lastName;
    private String userName;

   private Long membership_id;
   private String membershipDescription;

}

