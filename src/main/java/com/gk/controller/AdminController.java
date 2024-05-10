package com.gk.controller;

import com.gk.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String adminHomePage() {
        return "adminHome";
    }

}
