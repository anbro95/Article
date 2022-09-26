package com.brovko.article.service;

import com.brovko.article.model.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    String deleteCategory(Long id);
    Category updateCategory(Category category );
}
