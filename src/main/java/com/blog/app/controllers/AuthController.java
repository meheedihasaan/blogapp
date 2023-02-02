package com.blog.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/signup")
    public String viewSignupPage(Model model) {
        model.addAttribute("title", "Mini Blog - Sign Up");

        return "blog-template/signup";
    }

    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        model.addAttribute("title", "Mini Blog - Login");

        return "blog-template/login";
    }

}
