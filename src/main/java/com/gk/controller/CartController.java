package com.gk.controller;

import com.gk.common.GlobalData;
import com.gk.model.Product;
import com.gk.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;

    @GetMapping("addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id) {
        GlobalData.cart.add(productService.findProductById(id));
        return "redirect:/shop";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("total",  GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @GetMapping("/viewCartDetails")
    public String viewCartDetails(Model model) {
        model.addAttribute("cartCount",  GlobalData.cart.size());
        model.addAttribute("total",  GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",  GlobalData.cart);
        return "cart";
    }

    @GetMapping("/removeItem/{index}")
    public String removeItem(@PathVariable("index") int id) {
        GlobalData.cart.remove(id);
        return "redirect:/cart/viewCartDetails";
    }
}
