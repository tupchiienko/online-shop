package org.cursor.shopservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.CategoryDto;
import org.cursor.data.model.Category;
import org.cursor.shopservice.repository.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ObjectMapper mapper;

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        return categoryRepository.save(mapper.convertValue(categoryDto, Category.class));
    }

    @Override
    public void updateCategory(UUID id, CategoryDto categoryDto) {
        categoryRepository.findById(id).orElseThrow();
        var category = mapper.convertValue(categoryDto, Category.class);
        category.setId(id);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.findById(id).orElseThrow();
        categoryRepository.deleteById(id);
    }
}
