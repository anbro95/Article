package com.brovko.article.dto.mappers;

import com.brovko.article.dto.models.UserDTO;
import com.brovko.article.model.Article;
import com.brovko.article.model.Job;
import com.brovko.article.model.Role;
import com.brovko.article.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "articleNames", source = "articles"),
            @Mapping(target = "roles", source = "roles"),
            @Mapping(target = "jobs", source = "jobs"),
            @Mapping(target = "followingNames", source = "following"),
            @Mapping(target = "followersNames", source = "followers")
    })
    UserDTO toDTO (User user);

    @Mappings({
            @Mapping(target = "articleNames", source = "articles"),
            @Mapping(target = "roles", source = "roles"),
            @Mapping(target = "jobs", source = "jobs"),
            @Mapping(target = "followingNames", source = "following"),
            @Mapping(target = "followersNames", source = "followers")
    })
    List<UserDTO> UsersToUsersDTO(List<User> users);

    default String mapArticleToString(Article article) {
        return article.getName();
    }

    default String mapRoleToString(Role role) {
        return role.getName();
    }

    default String mapJobToString(Job job) {
        return job.getJobName();
    }

    default String mapUserToString(User user) {
        return user.getUserName();
    }
}
