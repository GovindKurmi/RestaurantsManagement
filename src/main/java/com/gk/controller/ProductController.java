package com.gk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String getProducts() {
        return "products";
    }

    @GetMapping("/products/add")
    public String getProductsAdd() {
        return "productsAdd";
    }

    @GetMapping("/products/update/{id}")
    public String getProductsUpdate() {
        return "productsUpdate";
    }

    @GetMapping("/products/delete/{id}")
    public String getProductsDelete() {
        return "productsDelete";
    }
}
