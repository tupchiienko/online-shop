package org.cursor.shopservice.service;

import org.cursor.data.dto.CategoryDto;
import org.cursor.data.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category getCategoryById(UUID id);

    List<Category> getAllCategories();

    Category createCategory(CategoryDto categoryDto);

    void updateCategory(UUID id, CategoryDto categoryDto);

    void deleteCategory(UUID id);
}
