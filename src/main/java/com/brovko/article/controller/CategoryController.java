package com.brovko.article.controller;


import com.brovko.article.model.Category;
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
    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping("/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/categories").toUriString());
        return ResponseEntity.created(uri).body(categoryServiceImpl.saveCategory(category));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Category category = categoryServiceImpl.getCategoryById(id);
        return ResponseEntity.ok().body(category == null ? "Category " + id + " not found" : category);
    }

    @GetMapping("/categories/name/{name}")
    public ResponseEntity<?> getCategoryById(@PathVariable String name) {
        Category category = categoryServiceImpl.getCategoryByName(name);
        return ResponseEntity.ok().body(category == null ? "Category '" + name + "' not found" : category);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok().body(categoryServiceImpl.getAllCategories());
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return categoryServiceImpl.deleteCategory(id);
    }

    @PutMapping("/categories")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        Category updatedCategory = categoryServiceImpl.updateCategory(category);
        return ResponseEntity.ok().body(updatedCategory == null ? "Could not update category " +
                                                                    category.getCategory_id() : updatedCategory);
    }
}
