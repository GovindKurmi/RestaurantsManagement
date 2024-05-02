package com.gk.controller;

import com.gk.model.Product;
import com.gk.model.ProductDto;
import com.gk.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public String addProducts(@ModelAttribute("productDTO") ProductDto productDto, @RequestParam("productImage") MultipartFile file,
                              @RequestParam("imgName") String imgName) throws IOException {
        productService.addProduct(productDto, file, imgName);
        return "redirect:/product/getProducts";
    }

    @GetMapping("/delete/{id}")
    public String getProductsDelete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "products";
    }

    @PostMapping("/update/{id}")
    public String updateProducts(@ModelAttribute("productDTO") ProductDto productDto, @PathVariable Long id) {
        productService.updateProducts(productDto);
        return "products";
    }
}
