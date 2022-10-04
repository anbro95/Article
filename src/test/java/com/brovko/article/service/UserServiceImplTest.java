package com.brovko.article.service;

import com.brovko.article.messaging.RabbitMQSender;
import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import com.brovko.article.repository.ArticleRepository;
import com.brovko.article.repository.JobRepository;
import com.brovko.article.repository.RoleRepository;
import com.brovko.article.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    RabbitMQSender sender;

    @Mock
    JobRepository jobRepository;
    @Mock
    ArticleRepository articleRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userService = spy(new UserServiceImpl(userRepository,roleRepository, passwordEncoder,
                articleRepository, jobRepository, sender));
    }

    @Test
    void save_role() {
        Long role_id = 1l;
        Role role = new Role();
        role.setId(role_id);

        doReturn(role).when(roleRepository).save(role);

        Assertions.assertThat(userService.saveRole(role)).isEqualTo(role);
        verify(roleRepository).save(role);
    }

    @Test
    void save_user() {
        Long user_id = 2L;
        User user = User.builder().user_id(user_id).build();

        doReturn(user).when(userRepository).save(user);

        Assertions.assertThat(userService.saveUser(user)).isEqualTo(user);
        verify(userRepository).save(user);
    }

    @Test

    void roles() {
        List<Role> roles = Arrays.asList(new Role("test_1"), new Role("test_2"));
        doReturn(roles).when(roleRepository).findAll();

        Assertions.assertThat(userService.roles()).isEqualTo(roles);
        verify(roleRepository).findAll();
    }

    @Test
    void get_user_by_id() {
        Long user_id = 3L;
        User user = User.builder().user_id(user_id).build();

        doReturn(Optional.of(user)).when(userRepository).findById(user_id);

        Assertions.assertThat(userService.getUserById(user_id)).isEqualTo(user);
        verify(userRepository).findById(user_id);
    }

    @Test
    void get_all_users() {
        List<User> users = Arrays.asList(User.builder().userName("shkaff02").build(),
                User.builder().userName("brewww").build());
        doReturn(users).when(userRepository).findAll();

        Assertions.assertThat(userService.getAllUsers()).isEqualTo(users);
        verify(userRepository).findAll();
    }

    @Test
    void delete_user_by_id() {
        Long id = 1L;
        userService.deleteUserById(id);
        verify(userRepository).deleteById(id);
    }

}
