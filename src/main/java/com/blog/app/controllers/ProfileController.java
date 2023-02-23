package com.blog.app.controllers;

import com.blog.app.helper.Message;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{username}/edit")
    public String viewEditProfilePage(Model model, Principal principal) {
        model.addAttribute("title", "Mini Blog - Edit Profile");
        loadCommonData(model, principal);

        UserDto user = this.userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("demoPass", "1234");

        return "admin-template/edit-profile";
    }

    @PostMapping("/edit-process")
    public String signUp(
            @Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal)
    {
        model.addAttribute("title", "Mini Blog - Edit Profile");
        loadCommonData(model, principal);
        try{
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                return "admin-template/edit-profile";
            }

            this.userService.updateUser(user, user.getId());
            return "blog-template/edit-profile-success";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            UserDto existing = this.userService.getUserByEmail(principal.getName());
            return "redirect:/admin-panel/profile/"+existing.getName();
        }
    }

}
