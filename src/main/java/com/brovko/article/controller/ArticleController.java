package com.brovko.article.controller;


import com.brovko.article.model.Article;
import com.brovko.article.service.ArticleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleServiceImpl articleServiceImpl;

    @PostMapping("/articles/{user_id}")
    public ResponseEntity<Article> saveArticle(@RequestBody Article article,
                                               @PathVariable Long user_id) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/articles").toUriString());
        return ResponseEntity.created(uri).body(articleServiceImpl.saveArticle(article, user_id));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        Article article = articleServiceImpl.getArticleById(id);
        return ResponseEntity.ok().body(article == null ? "Article " + id + " not found" : article);
    }

    @GetMapping("/articles/name/{name}")
    public ResponseEntity<?> getArticleByName(@PathVariable String name) {
        Article article = articleServiceImpl.getArticleByName(name);
        return ResponseEntity.ok().body(article == null ? "Article '" + name + "' not found" : article);
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticleById(@PathVariable Long id) {
        return articleServiceImpl.deleteArticleById(id);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok().body(articleServiceImpl.getAllArticles());
    }

    @PutMapping("/articles")
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        Article updatedArticle = articleServiceImpl.updateArticle(article);
        return ResponseEntity.ok().body(updatedArticle == null ? "Article " + article.getArticle_id() + " not found" : article);
    }

//    @PutMapping("/articles/{id}/category/{cid}")
//    public String addCategoryToArticle(@PathVariable (value = "id") Long articleId,
//                                       @PathVariable(value = "cid") Long categoryId) {
//
//        return articleService.addCategoryToArticle(articleId, categoryId);
//    }

}
