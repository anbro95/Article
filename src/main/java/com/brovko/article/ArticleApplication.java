package com.brovko.article;

import com.brovko.article.model.Article;
import com.brovko.article.model.User;
import com.brovko.article.service.ArticleService;
import com.brovko.article.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ArticleService articleService) {
        return args -> {
            articleService.saveArticle(new Article(1l, "Пингвины дохнут", "Очень много пингвинов дохнет последнее время"));
            articleService.saveArticle(new Article(2l, "Гендеров не 2", "Лично я по гендеру балкон"));
        };
    }

}
