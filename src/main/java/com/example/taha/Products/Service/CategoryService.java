package com.example.taha.Products.Service;

import com.example.taha.Products.Model.Category;
import com.example.taha.Products.Model.CategoryDto;
import com.example.taha.Products.Repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    public Page<CategoryDto> getAllCategories(int page) {
        return categoryRepo.findAll(PageRequest.of(page, 5))
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                ));
    }

    public CategoryDto createCategory(Category category) {
        Category savedCategory = categoryRepo.save(category);
        return new CategoryDto(
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.getDescription()
        );
    }

    public Optional<CategoryDto> getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                ));
    }

    public Optional<CategoryDto> updateCategory(Long id, Category newCategory) {
        return categoryRepo.findById(id).map(category -> {
            category.setName(newCategory.getName());
            category.setDescription(newCategory.getDescription());

            Category updatedCategory = categoryRepo.save(category);
            return new CategoryDto(
                    updatedCategory.getId(),
                    updatedCategory.getName(),
                    updatedCategory.getDescription()
            );
        });
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }
}

