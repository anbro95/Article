package com.brovko.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String password;
    private LocalDate createdAt;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private int age;
    private String country;
    private String city;

//    private List<String> socialMediaLinks;
    private String creditCardNumber;

    private String lastName;
    private String userName;
//    private List<Article> articles;
//    private Job job;

}
