package com.brovko.article.repository;

import com.brovko.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleByName(String name);
    List<Article> getArticlesByPremium(boolean isPremium);
}
