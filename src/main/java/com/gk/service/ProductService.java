package com.gk.service;

import com.gk.model.Category;
import com.gk.model.Product;
import com.gk.model.ProductDto;
import com.gk.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public void addProduct(ProductDto productDto, MultipartFile file, String imgName) throws IOException {
        Product product = new Product();
        Category categoryById = findCategoryById(productDto.getCategory().getId());
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryById);
        product.setWeight(productDto.getWeight());
        String originalFilename = "";
        if (!file.isEmpty()) {
            originalFilename = file.getOriginalFilename();
            Path path = Paths.get("src\\main\\resources\\static\\images\\" + originalFilename);
            Files.write(path, file.getBytes());
        } else {
            originalFilename = imgName;
        }
        product.setImageName(originalFilename);
        productRepository.save(product);
    }

    private Category findCategoryById(Long id) {
        return categoryService.findById(id).orElse(new Category(1l, null, null));
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProducts(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());
        product.setCategory(findCategoryById(productDto.getCategory().getId()));
        productRepository.save(product);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findProductByCategory(Category category) {
       return getAllProduct().stream().filter(p->p.getCategory().getId().equals(category.getId())).toList();
    }
}
