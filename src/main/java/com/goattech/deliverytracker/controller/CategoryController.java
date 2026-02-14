package com.goattech.deliverytracker.controller;

import com.goattech.deliverytracker.model.Category;
import com.goattech.deliverytracker.service.CategoryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories(@RequestParam String type) {
        return categoryService.getCategories(type);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return categoryService.createCategory(category, userId);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody Category category,
            @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return categoryService.updateCategory(id, category, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        categoryService.deleteCategory(id, userId);
    }
}
