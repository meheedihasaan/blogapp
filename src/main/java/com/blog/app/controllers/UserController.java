package com.blog.app.controllers;

import com.blog.app.helper.ApiResponse;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //To create a user
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto savedUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    //To get all the users
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> usersDto = this.userService.getAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    //To get a user by his id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id){
        UserDto userDto = this.userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //To update a user
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int id){
        UserDto updatedUserDto = this.userService.updateUser(userDto, id);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //To delete a user by his id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
        this.userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully.", true) , HttpStatus.OK);
    }

}

