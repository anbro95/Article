package com.brovko.article.service;

import com.brovko.article.model.Article;
import com.brovko.article.model.Category;
import com.brovko.article.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @BeforeEach
    void setUp() {
        categoryService = spy(new CategoryServiceImpl(categoryRepository));
    }


    @Test
    void save_category() {
        Long id = 1L;
        Category category = new Category(id, "comedy", "funny", Arrays.asList(new Article()));
        doReturn(category).when(categoryRepository).save(category);
        Assertions.assertThat(categoryService.saveCategory(category)).isNotNull();
        verify(categoryRepository).save(category);
    }

    @Test
    void get_category_by_id() {
        Long id = 1L;
        Category category = new Category(id, "comedy", "funny", Arrays.asList(new Article()));

        doReturn(Optional.of(category)).when(categoryRepository).findById(id);
        Assertions.assertThat(categoryService.getCategoryById(id)).isEqualTo(category);
        verify(categoryRepository).findById(id);
    }

    @Test
    void get_all_categories() {
        List<Category> categories = Arrays.asList(new Category(1L, "comedy", "funny",
                                                  Arrays.asList(new Article())));
        doReturn(categories).when(categoryRepository).findAll();
        Assertions.assertThat(categoryService.getAllCategories()).isEqualTo(categories);
        verify(categoryRepository).findAll();
    }

    @Test
    void delete_category_by_id() {
        categoryService.deleteCategory(1L);
        verify(categoryRepository).deleteById(1L);
    }

}