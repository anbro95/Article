package com.brovko.article.service;


import com.brovko.article.model.Article;
import com.brovko.article.model.User;
import com.brovko.article.repository.ArticleRepository;
import com.brovko.article.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public User saveUser(User user) {
        log.info("Saving user with id {}", user.getId());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        log.info("Looking for user with id {}", id);
        return userRepository.findById(id).orElse(null);

    }

    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public User getUserByUserName(String userName) {
        log.info("Getting user by Username {}", userName);
        return userRepository.findByUserName(userName).orElse(null);
    }

    public String deleteUserById(Long id) {
        log.info("Deleting user by ID {}", id);
        try{
            userRepository.deleteById(id);
            return "Successfully deleted!!!";
        } catch (Exception e) {
            return "User not found with id " + id;
        }

    }

    public User updateUser(User user) {
        log.info("Updating user info {}", user.getId());
        Long id = user.getId();
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null) {
            return null;
        }

        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setCity(user.getCity());
        updatedUser.setAge(user.getAge());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setCountry(user.getCountry());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setBirthDate(user.getBirthDate());
        updatedUser.setCreatedAt(user.getCreatedAt());

        updatedUser.setPhone(user.getPhone());
//        updatedUser.setArticles(user.getArticles());
//        updatedUser.setSocialMediaLinks(user.getSocialMediaLinks());
        updatedUser.setCreditCardNumber(user.getCreditCardNumber());
//        updatedUser.setJob(user.getJob());

        return userRepository.save(updatedUser);
    }

    public String addArticleToUser(Long userId, Long articleId) {
        User user = userRepository.findById(userId).orElse(null);
        Article article = articleRepository.findById(articleId).orElse(null);

        if(user == null) return "User " + userId + " not found";
        if(article == null) return "Article " + articleId + " not found";

        user.getArticles().add(article);
//        article.setAuthor(user);
        userRepository.save(user);

        return "Article added to user successfully!";
    }
}
