package com.blog.app.services;

import com.blog.app.payloads.UserDto;
import java.util.List;

public interface UserService {

    //To create a user
    public UserDto createUser(UserDto userDto);

    //To get all the users
    public List<UserDto> getAllUsers();

    //To get a user by his id
    public UserDto getUserById(int id);

    //To update a user
    public UserDto updateUser(UserDto userDto, int id);

    //To delete a user by his id
    public void deleteUser(int id);

    //To register a user
    public UserDto signupUser(UserDto userDto);

    //To get a user by his email
    public UserDto getUserByEmail(String email);

}
