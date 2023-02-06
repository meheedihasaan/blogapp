package com.blog.app.controllers;

import com.blog.app.payloads.UserDto;
import com.blog.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin-panel")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Dashboard");

        String username = principal.getName();
        UserDto user = this.userService.getUserByEmail(username);  //Email is used as username
        model.addAttribute("user", user);

        return "admin-template/index";
    }

}
