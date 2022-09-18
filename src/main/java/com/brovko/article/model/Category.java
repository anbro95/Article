package com.brovko.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor

@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
//    @JoinTable(name = "articles_categories", joinColumns = {@JoinColumn(name = "categoryId")},
//            inverseJoinColumns = {@JoinColumn(name = "articleId")})
    private List<Article> articleList;


}
