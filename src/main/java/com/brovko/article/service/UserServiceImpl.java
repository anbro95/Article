package com.brovko.article.service;


import com.brovko.article.model.*;
import com.brovko.article.model.notification.EmailDetails;
import com.brovko.article.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final ArticleRepository articleRepository;
    private final JobRepository jobRepository;

    private final String SEND_NOTIFICATION_URL = "https://article-notification-service.herokuapp.com/sendMail";


    public String addArticleToUser(Long userId, Long articleId) {
        log.info("Trying to add article {} to user {}", articleId, userId);
        User user = userRepository.findById(userId).orElse(null);
        Article article = articleRepository.findById(articleId).orElse(null);

        if(user == null) return "User " + userId + " not found";
        if(article == null) return "Article " + articleId + " not found";

        user.getArticles().add(article);
        userRepository.save(user);

        return "Article added to user successfully!";
    }

    public String addJobToUser(Long userId, Long jobId) {
        log.info("Trying to add Job {} to user {}", jobId, userId);
        User user = userRepository.findById(userId).orElse(null);
        Job job = jobRepository.findById(jobId).orElse(null);

        if(user == null) return "User " + userId + " not found";
        if(job == null) return "Job " + jobId + " not found";

        user.getJobs().add(job);
        userRepository.save(user);

        return "Job added to user successfully!";
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public List<Role> roles() {
        log.info("Fetching all roles");
        return roleRepository.findAll();
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUserName(username).orElse(null);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }


    public User saveUser(User user) {
        log.info("Saving user with id {}", user.getUser_id());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sendEmailToCreatedUser(user);
        return userRepository.save(user);
    }
// TODO uncomment this
    private void sendEmailToCreatedUser(User user) {
        EmailDetails emailDetails = retrieveEmailDetails(user);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<EmailDetails> request = new HttpEntity<>(emailDetails);
        restTemplate.postForObject(SEND_NOTIFICATION_URL, request, EmailDetails.class);
    }

    private EmailDetails retrieveEmailDetails(User user) {
       return EmailDetails.builder()
                .recipient(user.getEmail())
                .msgBody("We are so happy that you decided to start your Article journey!")
                .subject("Thank you for registration!")
                .build();
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
        log.info("Updating user info {}", user.getUser_id());
        Long id = user.getUser_id();
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
        updatedUser.setPremium(user.isPremium());

        updatedUser.setPhone(user.getPhone());
        updatedUser.setArticles(user.getArticles());
        updatedUser.setCreditCardNumber(user.getCreditCardNumber());
        updatedUser.setJobs(user.getJobs());

        return userRepository.save(updatedUser);
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = getUserByUserName(username);
        return user;
    }


    public boolean checkUserArticleAccess(Article article) {
        User user = getCurrentUser();
        if(!article.isPremium()) {
            return true;
        } else {
            if (user.isPremium()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public User setPremiumUser(Long id, Map<String, Boolean> map){
        User user = userRepository.findById(id).orElse(null);
        if(user == null) return null;

        user.setPremium(map.get("premium"));
        return userRepository.save(user);
    }
}
