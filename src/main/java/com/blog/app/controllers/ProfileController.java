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
@RequestMapping("/admin-panel/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        UserDto user = this.userService.getUserByEmail(username); //Email is used as username
        model.addAttribute("user", user);
    }

    @GetMapping("/admin-panel/profile/{username}/edit")
    public String viewEditProfilePage(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Edit Profile");
        loadCommonData(model, principal);

        return "admin-template/edit-profile";
    }

}
