package com.brovko.article.controller;


import com.brovko.article.model.Article;
import com.brovko.article.repository.ArticleRepository;
import com.brovko.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<Article> saveArticle(@RequestBody Article article) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/articles").toUriString());
        return ResponseEntity.created(uri).body(articleService.saveArticle(article));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok().body(article == null ? "Article " + id + " not found" : article);
    }

    @GetMapping("/articles/name/{name}")
    public ResponseEntity<?> getArticleByName(@PathVariable String name) {
        Article article = articleService.getArticleByName(name);
        return ResponseEntity.ok().body(article == null ? "Article '" + name + "' not found" : article);
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok().body(articleService.getAllArticles());
    }

    @PutMapping("/articles")
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        Article updatedArticle = articleService.updateArticle(article);
        return ResponseEntity.ok().body(updatedArticle == null ? "Article " + article.getId() + " not found" : article);
    }

}
