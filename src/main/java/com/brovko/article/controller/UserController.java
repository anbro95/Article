package com.brovko.article.controller;

import com.brovko.article.dto.mappers.UserMapper;
import com.brovko.article.dto.models.UserDTO;
import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok().body(userService.roles());
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/roles").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }


    @PutMapping("/roles/users")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(UserMapper.USER_MAPPER.toDTO(userService.saveUser(user)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(user == null ? "User " + id + " not found" : UserMapper.USER_MAPPER.toDTO(user));
    }

    @GetMapping("/users/name/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        return ResponseEntity.ok().body(user == null ? "User \"" + userName + "\" not found" :
                UserMapper.USER_MAPPER.toDTO(user));
    }

    @DeleteMapping("/users/{id}")
    public String deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok().body(UserMapper.USER_MAPPER.UsersToUsersDTO(userService.getAllUsers()));
    }

    @PutMapping ("/users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok().body(updatedUser == null ? "User " + user.getUser_id() + " not found" :
                UserMapper.USER_MAPPER.toDTO(user));
    }

    @PutMapping("/users/{userId}/article/{articleId}")
    public String addArticleToUser(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "articleId") Long articleId) {
        return userService.addArticleToUser(userId, articleId);
    }

    @PutMapping("/users/{userId}/job/{jobId}")
    public String addJobToUser(@PathVariable(value = "userId") Long userId,
                                   @PathVariable(value = "jobId") Long jobId) {
        return userService.addJobToUser(userId, jobId);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<?> setPremiumUser(@PathVariable Long id, @RequestBody Map<String, Boolean> map){
        User user = userService.setPremiumUser(id, map);

        return ResponseEntity.ok().body(user == null ? "User " + id + " not found" :
                UserMapper.USER_MAPPER.toDTO(user));
    }

    @PutMapping("/users/{id}/follower/{id2}")
    public ResponseEntity<?> addFollowerToUser(@PathVariable(value = "id") Long id, @PathVariable(value = "id2") Long id2) {
        return ResponseEntity.ok().body(userService.addFollowerToUser(id, id2));
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}