package com.example.taha.Products.Service;

import com.example.taha.Products.Model.Category;
import com.example.taha.Products.Model.CategoryDto;
import com.example.taha.Products.Model.Product;
import com.example.taha.Products.Model.ProductResponseDto;
import com.example.taha.Products.Repo.CategoryRepo;
import com.example.taha.Products.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepository;

    public Page<ProductResponseDto> getAllProducts(int page) {
        return productRepo.findAll(PageRequest.of(page, 5))
                .map(product -> {
                    Category category = product.getCategory();
                    CategoryDto categoryDto = new CategoryDto(
                            category.getId(),
                            category.getName(),
                            category.getDescription()
                    );
                    return new ProductResponseDto(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            categoryDto
                    );
                });
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public Optional<ProductResponseDto> getProductById(Long id) {
        return productRepo.findById(id).map(product -> {
            Category category = product.getCategory();
            CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(),category.getDescription());

            return new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    categoryDto
            );
        });
    }

    public Optional<Product> updateProduct(Long id, Product newProduct) {
        return productRepo.findById(id).map(product -> {
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setCategory(newProduct.getCategory());
            return productRepo.save(product);
        });
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
