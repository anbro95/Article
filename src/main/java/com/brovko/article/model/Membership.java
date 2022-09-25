package com.brovko.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membership_id;

    private MembershipTypes membershipType;

    private String description;

    @OneToMany(mappedBy = "membership",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();


}

enum MembershipTypes {
    FULL,
    HALF,
    NONE
}