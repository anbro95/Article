package com.brovko.article;

import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.service.ArticleService;
import com.brovko.article.service.CategoryService;
import com.brovko.article.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(UserService userService) {

        return args -> {
            userService.saveRole(new Role("ROLE_USER"));
            userService.saveRole(new Role("ROLE_ADMIN"));

           userService.saveUser(new User("Kostia", "1234", "ksurygin5@gmail.com", "+380951427261",
                   LocalDate.of(2002, 1, 17), 20, "Ukraine", "Odesa", "5959599593995393", "Shuryhin",
                   "Shkaff02", new ArrayList<>()));
            userService.saveUser(new User("Andrew", "1234", "anbro2002@gmail.com",
                    "+380859427462",
                    LocalDate.of(2002, 6, 27), 20, "Ukraine", "Odesa",
                    "5959599593995393", "Brovko",
                    "Biba", new ArrayList<>()));

            userService.addRoleToUser("Shkaff02", "ROLE_USER");
            userService.addRoleToUser("Shkaff02", "ROLE_ADMIN");
            userService.addRoleToUser("Biba", "ROLE_USER");
        };
    }
}

