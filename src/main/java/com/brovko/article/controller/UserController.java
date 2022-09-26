package com.brovko.article.controller;


import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.service.UserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userServiceImpl.roles());
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles").toUriString());
        return ResponseEntity.created(uri).body(userServiceImpl.saveRole(role));
    }


    @PutMapping("/roles/users")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userServiceImpl.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(userServiceImpl.saveUser(user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userServiceImpl.getUserById(id);
        return ResponseEntity.ok().body(user == null ? "User " + id + " not found" : user);
    }

    @GetMapping("/users/name/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        User user = userServiceImpl.getUserByUserName(userName);
        return ResponseEntity.ok().body(user == null ? "User \"" + userName + "\" not found" : user);
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable Long id) {
        return userServiceImpl.deleteUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userServiceImpl.getAllUsers());
    }

    @PutMapping ("/users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User updatedUser = userServiceImpl.updateUser(user);
        return ResponseEntity.ok().body(updatedUser == null ? "User " + user.getUser_id() + " not found" : user);
    }

    @PutMapping("/users/{userId}/article/{articleId}")
    public String addArticleToUser(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "articleId") Long articleId) {
        return userServiceImpl.addArticleToUser(userId, articleId);
    }

    @PutMapping("/users/{userId}/job/{jobId}")
    public String addJobToUser(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "jobId") Long jobId) {
        return userServiceImpl.addJobToUser(userId, jobId);
    }
    @PutMapping("/users/{userId}/membership/{membershipId}")
    public String addMembershipToUser(@PathVariable(value = "membershipId") Long membershipId,
                                      @PathVariable(value = "userId") Long userId) {

        return userServiceImpl.addMembershipToUser(userId, membershipId);
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}