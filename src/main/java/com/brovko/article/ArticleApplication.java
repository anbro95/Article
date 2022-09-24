package com.brovko.article;

import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.User;
import com.brovko.article.service.ArticleService;
import com.brovko.article.service.CategoryService;
import com.brovko.article.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ArticleService articleService, CategoryService categoryService) {
        return args -> {
//            Category horror =  categoryService.saveCategory(new Category(1L, "Ужосы", "СТрашно"));
//            Category comedy =  categoryService.saveCategory(new Category(2L, "Комедия", "Смешно"));
//            List<Category> categoryList = Arrays.asList(horror, comedy);
////
//            articleService.saveArticle(new Article(1L, "Пингвины дохнут",
//                    "Очень много пингвинов дохнет последнее время"));
//            articleService.addCategorytoArticle(1L, 1L);
//            articleService.addCategorytoArticle(1L, 2L);
        };
    }

}
