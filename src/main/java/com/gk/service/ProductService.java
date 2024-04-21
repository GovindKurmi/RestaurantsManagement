package com.gk.service;

import com.gk.model.Category;
import com.gk.model.Product;
import com.gk.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }
}
