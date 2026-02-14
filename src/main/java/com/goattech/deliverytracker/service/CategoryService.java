package com.goattech.deliverytracker.service;

import com.goattech.deliverytracker.model.Category;
import com.goattech.deliverytracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(String type) {
        // RLS handles row filtering for user_id
        return categoryRepository.findByTypeAndIsActiveTrue(type);
    }

    @Transactional
    public Category createCategory(Category category, UUID userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is required");
        }
        category.setUserId(userId);
        category.setActive(true);
        // Ensure id is generated or set
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(UUID id, Category updatedCategory, UUID userId) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        validateOwnership(existingCategory, userId);

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setType(updatedCategory.getType());

        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteCategory(UUID id, UUID userId) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        validateOwnership(existingCategory, userId);

        categoryRepository.delete(existingCategory);
    }

    private void validateOwnership(Category category, UUID userId) {
        if (category.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot modify system categories");
        }
        if (!category.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }
}
