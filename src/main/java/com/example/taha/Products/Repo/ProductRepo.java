package com.example.taha.Products.Repo;

import com.example.taha.Products.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
