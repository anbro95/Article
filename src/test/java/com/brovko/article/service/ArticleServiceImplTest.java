package com.brovko.article.service;

import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.User;
import com.brovko.article.repository.ArticleRepository;
import com.brovko.article.repository.CategoryRepository;
import com.brovko.article.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private ArticleService articleService;
    @BeforeEach
    void setUp() {
        articleService = spy(new ArticleServiceImpl(articleRepository, categoryRepository, userService, userRepository));
    }

    // TODO dodelat'
    @Test
    void save_article() {
        Article article = Article.builder().article_id(1L).categories(new ArrayList<>()).user(User.builder().user_id(1L)
                                                                                        .build()).build();

        articleService.saveArticle(article, 1L, new ArrayList<>());
        verify(articleRepository).save(article);
    }

    @Test
    void get_article_by_id() {
        Long article_id = 2L;
        Article article = Article.builder().article_id(article_id).build();

        doReturn(Optional.of(article)).when(articleRepository).findById(article_id);

        Assertions.assertThat(articleService.getArticleById(article_id)).isEqualTo(article);
        verify(articleRepository).findById(article_id);

    }

    @Test
    void get_all_articles_not_premium() {
        List<Article> articles = Arrays.asList(Article.builder().article_id(1L).build());
        User user = User.builder().user_id(1L).password("12345").premium(false).build();

        doReturn(articles).when(articleRepository).getArticlesByPremium(false);
        doReturn(user).when(userService).getCurrentUser();

        Assertions.assertThat(articleService.getAllArticles()).isEqualTo(articles);
        verify(articleRepository).getArticlesByPremium(false);
    }


    @Test
    void delete_article_by_id() {
        articleService.deleteArticleById(1L);
        verify(articleRepository).deleteById(1L);
    }
}