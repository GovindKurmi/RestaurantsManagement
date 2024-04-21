package com.gk.controller;

import com.gk.model.Category;
import com.gk.model.Product;
import com.gk.model.ProductDto;
import com.gk.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getProducts")
    public String getProducts(Model model) {
        List<Product> allProduct = productService.getAllProduct();
        model.addAttribute("products", allProduct);
        return "products";
    }

    @GetMapping("/get_products/add_page")
    public String getProductsAdd(Model model) {
        ProductDto product = new ProductDto();
        model.addAttribute("productDTO", product);
        model.addAttribute("categories", productService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String addProducts(@ModelAttribute("productDTO") ProductDto productDto,
                              @RequestParam("productImage") MultipartFile file,
                              @RequestParam("imgName") String imgName) throws IOException {
        Product product = new Product();
        List<Category> allCategory = productService.getAllCategory();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(allCategory.get(0));
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
        productService.addProduct(product);
        return "redirect:/product/getProducts";
    }

    @GetMapping("/products/delete/{id}")
    public String getProductsDelete() {
        return "productsDelete";
    }
}
