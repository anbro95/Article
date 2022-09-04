package com.brovko.article.service;


import com.brovko.article.model.User;
import com.brovko.article.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    public User saveUser(User user) {
        log.info("Saving user with id {}", user.getId());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        log.info("Looking for user with id {}", id);
        return userRepository.findById(id).orElse(null);
    }
}
