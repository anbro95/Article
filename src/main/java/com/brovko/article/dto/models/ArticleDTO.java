package com.brovko.article.dto.models;

import com.brovko.article.service.CategoryService;
import com.brovko.article.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO{

    private String name;
    private boolean premium;
    private String text;

    private List<Long> categories_id;

    private String username;
}
