package com.blog.app.controllers;

import com.blog.app.helper.ApiResponse;
import com.blog.app.payloads.UserDto;
import com.blog.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //To add a user
    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
        UserDto savedUserDto = this.userService.addUser(userDto);
        return new ResponseEntity<UserDto>(savedUserDto, HttpStatus.CREATED);
    }

    //To get all the users
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtos = this.userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
    }

    //To get a user by his id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id){
        UserDto userDto = this.userService.getUserById(id);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    //To update a user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int id){
        UserDto updatedUserDto = this.userService.updateUser(userDto, id);
        return new ResponseEntity<UserDto>(updatedUserDto, HttpStatus.OK);
    }

    //To delete a user by his id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id){
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully.", true) , HttpStatus.OK);
    }

}

