package com.blog.app.services.implementation;

import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.UserDto;
import com.blog.app.repositories.UserRepository;
import com.blog.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //To add a user
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    
    //To get all ther users
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        //Convert users to userDtos
        List<UserDto> userDtos = users
                                    .stream()
                                    .map(user->  this.userToDto(user))
                                    .collect(Collectors.toList());
        return userDtos;
    }

    //To get a user by his id
    @Override
    public UserDto getUserById(int id) {
        User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        return this.userToDto(user);
    }

    //To update a user
    @Override
    public UserDto updateUser(UserDto userDto, int id) {
        User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setDescription(userDto.getDescription());

        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }

    //To delete a user by his id
    @Override
    public void deleteUser(int id) {
        User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    //To convert UserDto to User
    public User dtoToUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setDescription(userDto.getDescription());

        User user = this.modelMapper.map(userDto, User.class);

        return user;
    }

    //To convert User to UserDto
    public UserDto userToDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setDescription(user.getDescription());

        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        return userDto;
    }

}
