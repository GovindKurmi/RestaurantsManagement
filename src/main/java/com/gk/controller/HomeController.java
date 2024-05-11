package com.gk.controller;

import com.gk.common.GlobalData;
import com.gk.model.Category;
import com.gk.model.Product;
import com.gk.service.CategoryService;
import com.gk.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String sopping(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String soppingByCategory(Model model, @PathVariable("id") Long id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.findProductById(id));
        return "viewProduct";
    }

    @GetMapping("categories/{id}")
    public String getCategories(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id).orElseThrow();
        List<Product> products = productService.findProductByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategory());
        return "shop";
    }

    @GetMapping("/order/placed")
    public String viewCartDetails(Model model) {
        Map<String, String> receipt = new HashMap<>();
        receipt.put("total", String.valueOf(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum()));
        receipt.put("items", String.valueOf(GlobalData.cart.size()));
        receipt.put("discount", "10%");
        receipt.put("tax", "10%");
        receipt.put("delivery", "10%");

        model.addAttribute("parameters", receipt);
        return "orderPlaced";
    }


}
