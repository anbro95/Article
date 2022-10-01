package com.brovko.article.controller;


import com.brovko.article.dto.mappers.CategoryMapper;
import com.brovko.article.dto.models.CategoryDTO;
import com.brovko.article.model.Category;
import com.brovko.article.service.CategoryService;
import com.brovko.article.service.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/categories").toUriString());
        return ResponseEntity.created(uri).body(categoryService.saveCategory(category));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(category == null ? "Category " + id + " not found" :
                CategoryMapper.CATEGORY_MAPPER.toDTO(category));
    }

    @GetMapping("/categories/name/{name}")
    public ResponseEntity<?> getCategoryById(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok().body(category == null ? "Category '" + name + "' not found" :
                CategoryMapper.CATEGORY_MAPPER.toDTO(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok().body(CategoryMapper
                .CATEGORY_MAPPER.CategoriesToCategoriesDTO(categoryService.getAllCategories()));
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/categories")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok().body(updatedCategory == null ? "Could not update category " +
                                                                    category.getCategory_id() :
                                                                    CategoryMapper.CATEGORY_MAPPER.toDTO(updatedCategory));
    }
}
