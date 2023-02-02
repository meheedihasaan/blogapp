package com.blog.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin-panel")
public class AdminController {

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        model.addAttribute("title", "Mini Blog - Dashboard");

        return "admin-template/index";
    }

}
