package com.brovko.article.service;

import com.brovko.article.model.Role;
import com.brovko.article.model.User;

import java.util.List;

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
    String addMembershipToUser(Long userId, Long membershipId);
    User getCurrentUser();
}
