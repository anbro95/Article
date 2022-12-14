package com.brovko.article;

import com.brovko.article.messaging.RabbitMQSender;
import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
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
    CommandLineRunner run(UserServiceImpl userService) {

        return args -> {
            userService.saveRole(new Role("ROLE_ADMIN"));

            userService.saveUser(new User("Andrew", "1234", "anbro2002@gmail.com",
                    "+380859427462",
                    LocalDateTime.of(2002, Month.JUNE, 27, 18, 30), 20, "Ukraine", "Odesa",
                    "5959599593995393", "Brovko",
                    "Ruha", new ArrayList<>()));


            userService.addRoleToUser("Ruha", "ROLE_ADMIN");
        };
    }
}

