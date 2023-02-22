package com.blog.app.controllers;

import com.blog.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-panel/profile")
public class UserController {

    @Autowired
    private UserService userService;



}

