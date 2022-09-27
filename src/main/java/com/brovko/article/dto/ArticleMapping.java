package com.brovko.article.dto;

import com.brovko.article.model.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleMapping {
    private Long article_id;

    private String name;
    private String text;

    public static ArticleMapping convertToMapping(Article article) {
        return new ArticleMapping(article.getArticle_id(), article.getName(), article.getText());
    }
}
