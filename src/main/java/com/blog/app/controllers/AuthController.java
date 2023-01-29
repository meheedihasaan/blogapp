//package com.blog.app.controllers;
//
//import com.blog.app.exceptions.InvalidUsernameOrPasswordException;
//import com.blog.app.payloads.UserDto;
//import com.blog.app.security.JwtAuthRequest;
//import com.blog.app.security.JwtAuthResponse;
//import com.blog.app.security.JwtTokenHelper;
//import com.blog.app.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//public class AuthController {
//
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
//        this.authenticate(request.getUsername(), request.getPassword());
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//        JwtAuthResponse response = new JwtAuthResponse();
//        response.setToken(token);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    //To authenticate a user
//    private void authenticate(String username, String password) throws Exception {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        try {
//            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        }
//        catch (BadCredentialsException e) {
//            System.out.println("Invalid username or password!");
//            throw new InvalidUsernameOrPasswordException("Invalid username or password!");
//        }
//    }
//
//    //To register a new user
//    @PostMapping("/register")
//    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
//        UserDto registeredUserDto = this.userService.registerUser(userDto);
//        return new ResponseEntity<>(registeredUserDto, HttpStatus.CREATED);
//    }
//
//}
