package com.brovko.article.service;


import com.brovko.article.model.Article;
import com.brovko.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article saveArticle(Article article){
        log.info("Saving Article with id {}", article.getId());
        return articleRepository.save(article);
    }

    public Article getArticleById(Long id) {
        log.info("Looking for Article with id {}", id);
        return articleRepository.findById(id).orElse(null);
    }

    public List<Article> getAllArticles() {
        log.info("Getting list of all Articles");
        return articleRepository.findAll();
    }

    public Article getArticleByName(String name) {
        log.info("Loking for an Article with name {}", name);
        return articleRepository.findArticleByName(name).orElse(null);
    }

    public String deleteArticleById(Long id) {
        log.info("Trying to delete an Article with id {}", id);
        try{
            articleRepository.deleteById(id);
            return "Article was deleted successfully!";
        } catch (Exception e) {
            return "Article with id " + id + " not found";
        }
    }

    public Article updateArticle(Article article) {
        Long id = article.getId();
        log.info("Updating Article with id {}", id);

        Article updatedArticle = articleRepository.findById(id).orElse(null);

        if(updatedArticle == null) {
            return null;
        }

        updatedArticle.setName(article.getName());
        updatedArticle.setText(article.getText());
        return articleRepository.save(updatedArticle);
    }
}
