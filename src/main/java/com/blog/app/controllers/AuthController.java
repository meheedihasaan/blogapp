package com.blog.app.controllers;

import com.blog.app.helper.Message;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String viewSignupPage(Model model) {
        model.addAttribute("title", "Mini Blog - Sign Up");
        model.addAttribute("user", new UserDto());

        return "blog-template/signup";
    }
    @PostMapping("/signup-process")
    public String signUp(
            @Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
            RedirectAttributes redirectAttributes,
            Model model)
    {
        try{
            if (bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                return "blog-template/signup";
            }

            if (!agreement) {
                throw new Exception("You have not agreed with terms and condition. Please re-fill the form and check the terms and condition to sign up.");
            }

            UserDto existing = this.userService.getUserByEmail(user.getEmail());
            if (existing!= null){
                redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Your email already exists in another account! Please enter a new email address to sign up."));
            }
            else {
                this.userService.signupUser(user);
                redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Congratulations! You have successfully registered."));
            }

            return "redirect:/signup";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
            return "redirect:/signup";
        }
    }

    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        model.addAttribute("title", "Mini Blog - Login");

        return "blog-template/login";
    }

}
