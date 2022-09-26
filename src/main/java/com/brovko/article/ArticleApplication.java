package com.brovko.article;

import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
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
    CommandLineRunner run(UserServiceImpl userServiceImpl) {

        return args -> {
            userServiceImpl.saveRole(new Role("ROLE_USER"));
            userServiceImpl.saveRole(new Role("ROLE_ADMIN"));

           userServiceImpl.saveUser(new User("Kostia", "1234", "ksurygin5@gmail.com", "+380951427261",
                   LocalDate.of(2002, 1, 17), 20, "Ukraine", "Odesa", "5959599593995393", "Shuryhin",
                   "Shkaff02", new ArrayList<>()));
            userServiceImpl.saveUser(new User("Andrew", "1234", "anbro2002@gmail.com",
                    "+380859427462",
                    LocalDate.of(2002, 6, 27), 20, "Ukraine", "Odesa",
                    "5959599593995393", "Brovko",
                    "Biba", new ArrayList<>()));

            userServiceImpl.addRoleToUser("Shkaff02", "ROLE_USER");
            userServiceImpl.addRoleToUser("Shkaff02", "ROLE_ADMIN");
            userServiceImpl.addRoleToUser("Biba", "ROLE_USER");
        };
    }
}

