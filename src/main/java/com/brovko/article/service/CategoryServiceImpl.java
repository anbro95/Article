package com.brovko.article.service;

import com.brovko.article.model.Category;
import com.brovko.article.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        log.info("Trying to save category {}", category.getCategory_id());
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category getCategoryByName(String name) {
        log.info("Looking for category with name {}", name);
        Category category = categoryRepository.findCategoryByName(name).orElse(null);
        return category;
    }

    public List<Category> getAllCategories() {
        log.info("Trying to get list of all categories");
        return categoryRepository.findAll();
    }

    public String deleteCategory(Long id) {
        log.info("Trying to delete category with id {}", id);
        try{
            categoryRepository.deleteById(id);
            return "Category deleted successfully!";
        } catch(Exception e) {
            return "Could not delete category with id " + id;
        }
    }

    public Category updateCategory(Category category ) {
        Long id = category.getCategory_id();
        log.info("Trying to delete categiry with id {}", id);
        Category updatedCategory = categoryRepository.findById(id).orElse(null);
        if(updatedCategory == null) return null;

        updatedCategory.setDescription(category.getDescription());
        updatedCategory.setName(category.getName());

        return categoryRepository.save(updatedCategory);
    }
}
