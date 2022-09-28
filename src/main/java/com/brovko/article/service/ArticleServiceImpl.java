package com.brovko.article.service;


import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.User;
import com.brovko.article.repository.ArticleRepository;
import com.brovko.article.repository.CategoryRepository;
import com.brovko.article.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public Article saveArticle(Article article, Long id){
        log.info("Saving Article with id {}", article.getArticle_id());
        // temp
        article.getCategories_id().forEach(category_id -> article
                .getCategories().add(categoryRepository.findById(category_id).orElse(null)));
        article.setUser(userRepository.findById(id).orElse(null));
        return articleRepository.save(article);
    }

    public Article getArticleById(Long id) {
        log.info("Looking for Article with id {}", id);
        return articleRepository.findById(id).orElse(null);
    }



    public List<Article> getAllArticles() {
        log.info("Getting list of all Articles");
        User user = userService.getCurrentUser();
        if(!user.isPremium()) {
            return getArticlesByPremium(false);
        } else {
            return getArticlesByPremium(true);
        }
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
        Long id = article.getArticle_id();
        log.info("Updating Article with id {}", id);

        Article updatedArticle = articleRepository.findById(id).orElse(null);

        if(updatedArticle == null) {
            return null;
        }

        updatedArticle.setPremium(article.isPremium());
        updatedArticle.setName(article.getName());
        updatedArticle.setText(article.getText());
        return articleRepository.save(updatedArticle);
    }

    public String addCategoryToArticle(Long articleId, Long categoryId) {
        log.info("Trying to add category {} to article {}", categoryId, articleId);
        Article article = articleRepository.findById(articleId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if(article == null) return "Article " + articleId + " not found";
        if(category == null) return "Category " + categoryId + " not found";

        category.getArticles().add(article);
        categoryRepository.save(category);

        return "Category added to article successfully";
    }

    public List<Article> getArticlesByPremium(boolean isPremium){
        List<Article> free = articleRepository.getArticlesByPremium(false);
        if(isPremium) {
            List<Article> premiums = articleRepository.getArticlesByPremium(true);
            return Stream.concat(premiums.stream(), free.stream()).toList();
        } else{
            return free;
        }
    }
}
