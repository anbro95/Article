package com.brovko.article.service;

import com.brovko.article.model.Article;

import java.util.List;

public interface ArticleService {
    Article saveArticle(Article article, Long id);
    Article getArticleById(Long id);
    List<Article> getAllArticles();
    Article getArticleByName(String name);
    String deleteArticleById(Long id);
    Article updateArticle(Article article);
    String addCategoryToArticle(Long articleId, Long categoryId);
    List<Article> getArticlesByPremium(boolean isPremium);

}
