package com.brovko.article.service;

import com.brovko.article.model.Article;
import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.model.notification.EmailDetails;

import java.util.List;
import java.util.Map;

public interface UserService {
    String addArticleToUser(Long userId, Long articleId);
    String addJobToUser(Long userId, Long jobId);
    Role saveRole(Role role);
    List<Role> roles();
    void addRoleToUser(String username, String roleName);
    User saveUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User getUserByUserName(String userName);
    String deleteUserById(Long id);
    User updateUser(User user);
    User getCurrentUser();
    boolean checkUserArticleAccess(Article article);
    User setPremiumUser(Long id, Map<String, Boolean> map);
    String addFollowerToUser(Long userId, Long followerId);
    void sendEmailToFollowers(Article article);
}
