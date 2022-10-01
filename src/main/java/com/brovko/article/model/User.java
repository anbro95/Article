package com.brovko.article.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "user_id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @NotNull
    private String firstName;
    @NotNull
    private String password;
    private LocalDateTime createdAt;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    private LocalDateTime birthDate;
    private int age;
    @NotNull
    private String country;
    @NotNull
    private String city;

    private String twitterLink;
    private String instagramLink;
    private String facebookLink;
    @NotNull
    private String creditCardNumber;

    private boolean premium;
    @NotNull
    private String lastName;
    @NotNull
    private String userName;
    @OneToMany(mappedBy = "user",
                cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public User(String firstName, String password, String email, String phone, LocalDateTime birthDate,
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
        this.createdAt = LocalDateTime.now();
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "users_jobs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private List<Job> jobs;
    @ManyToMany()
    @JoinTable(
            name = "users_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following")
    )
    private List<User> following;

    @ManyToMany()
    @JoinTable(
            name = "users_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "followers")
    )
    private List<User> followers;
}
