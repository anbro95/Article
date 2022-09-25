package com.brovko.article.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

//    private List<String> socialMediaLinks;
    private String creditCardNumber;

    private String lastName;
    private String userName;
    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Role> roles = new ArrayList<>();

    public User(String firstName, String password, String email, String phone, LocalDate birthDate,
                int age, String country, String city, String creditCardNumber, String lastName,
                String userName, Collection<Role> roles) {
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.age = age;
        this.country = country;
        this.city = city;
        this.creditCardNumber = creditCardNumber;
        this.lastName = lastName;
        this.userName = userName;
        this.roles = roles;
        this.createdAt = LocalDate.now();
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "users_jobs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<Job> jobs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;
}
