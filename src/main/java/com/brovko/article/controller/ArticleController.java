package com.brovko.article.controller;


import com.brovko.article.dto.mappers.ArticleMapper;
import com.brovko.article.dto.models.ArticleDTO;
import com.brovko.article.filter.MyAuthenticationFilter;
import com.brovko.article.model.Article;
import com.brovko.article.model.User;
import com.brovko.article.service.ArticleService;
import com.brovko.article.service.ArticleServiceImpl;
import com.brovko.article.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    @PostMapping("/articles/{user_id}")
    public ResponseEntity<ArticleDTO> saveArticle(@RequestBody ArticleDTO articleDTO,
                                               @PathVariable Long user_id) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/articles").toUriString());
        return ResponseEntity.created(uri).body(ArticleMapper.ARTICLE_MAPPER.toDTO(articleService
                .saveArticle(ArticleMapper.ARTICLE_MAPPER.fromDTO(articleDTO), user_id, articleDTO.getCategories_id())));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        if(article == null) {
            return ResponseEntity.ok().body("Article with id " + id + " not found");
        } else {
            if(userService.checkUserArticleAccess(article)) {
                return ResponseEntity.ok().body(ArticleMapper.ARTICLE_MAPPER.toDTO(article));
            } else {
                return ResponseEntity.ok().body("Sorry, you do not have Premium Membership to view this article");
            }
        }
    }

    @GetMapping("/articles/name/{name}")
    public ResponseEntity<?> getArticleByName(@PathVariable String name) {
        Article article = articleService.getArticleByName(name);
        return ResponseEntity.ok().body(article == null ? "Article '" + name + "' not found" :
                ArticleMapper.ARTICLE_MAPPER.toDTO(article));
    }

    @DeleteMapping("/articles/{id}")
    public String deleteArticleById(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return ResponseEntity.ok().body(ArticleMapper.ARTICLE_MAPPER.ArticlesToArticlesDTO(articleService
                .getAllArticles()));
    }

    @PutMapping("/articles")
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        Article updatedArticle = articleService.updateArticle(article);
        return ResponseEntity.ok().body(updatedArticle == null ? "Article " + article.getArticle_id() + " not found"
                : ArticleMapper.ARTICLE_MAPPER.toDTO(article));
    }

}
