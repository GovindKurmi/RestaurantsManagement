package com.gk.controller;

import com.gk.service.CategoryService;
import com.gk.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/shop")
    public String sopping(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String soppingByCategory(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productService.findProductById(id));
        return "viewProduct";
    }

    @GetMapping("addToCart/{id}")
    public String addToCart(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productService.findProductById(id));
        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "checkout";
    }

}
