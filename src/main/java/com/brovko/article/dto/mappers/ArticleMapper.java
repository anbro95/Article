package com.brovko.article.dto.mappers;

import com.brovko.article.dto.models.ArticleDTO;
import com.brovko.article.dto.models.CategoryDTO;
import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface ArticleMapper {
    ArticleMapper ARTICLE_MAPPER = Mappers.getMapper(ArticleMapper.class);

    Article fromDTO(ArticleDTO articleDTO);

    @Mapping(target = "username", source = "user")
    @Mapping(target = "categories_id", source = "categories")
    ArticleDTO toDTO (Article article);

    @Mapping(target = "username", source = "user")
    @Mapping(target = "categories_id", source = "categories")
    List<ArticleDTO> ArticlesToArticlesDTO(List<Article> articles);

    default String mapUserToString(User user) {
        return user.getUserName();
    }

    default Long mapCategoryToLong(Category category) {
        return category.getCategory_id();
    }

}
