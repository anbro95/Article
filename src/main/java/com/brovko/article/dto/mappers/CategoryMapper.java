package com.brovko.article.dto.mappers;

import com.brovko.article.dto.models.CategoryDTO;
import com.brovko.article.dto.models.UserDTO;
import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "articles", source = "articles")
    CategoryDTO toDTO (Category category);


    @Mapping(target = "articles", source = "articles")
    List<CategoryDTO> CategoriesToCategoriesDTO(List<Category> categories);

    default Long mapArticleToLong(Article article) {
        return article.getArticle_id();
    }
}
