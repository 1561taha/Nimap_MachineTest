package com.example.taha.Products.Service;

import com.example.taha.Products.Model.Category;
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

    public Page<Category> getAllCategories(int page) {
        return categoryRepo.findAll(PageRequest.of(page, 5));
    }

    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepo.findById(id);
    }

    public Optional<Category> updateCategory(Long id, Category newCategory) {
        return categoryRepo.findById(id).map(category -> {
            category.setName(newCategory.getName());
            return categoryRepo.save(category);
        });
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }
}

