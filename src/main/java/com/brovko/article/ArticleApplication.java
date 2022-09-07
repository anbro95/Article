package com.brovko.article;

import com.brovko.article.model.User;
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
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveUser(new User(1L, "Andrew", "Brovko", "Jun", "anbro"));
            userService.saveUser(new User(2L, "Volodya", "Zelenskiy", "Prez", "zelya"));
        };
    }

}
