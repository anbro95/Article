package com.brovko.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String name;
    private String text;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
//    private User author;


    public Article(Long articleId, String name, String text) {
        this.articleId = articleId;
        this.name = name;
        this.text = text;
    }
}
