package com.blog.app.services;

import com.blog.app.entities.User;
import com.blog.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //To add a user
    public User addUser(User user){
        return userRepository.save(user);
    }

    //To get all the users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //To get a user by his id
    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
//        Optional<User> optional = userRepository.findById(id);
//        if(optional.isPresent()){
//            return optional.get();
//        }
//        else{
//            return null;
//        }
    }

    //To update a user
    public User updateUser(User user){
        User existing = userRepository.findById(user.getId()).orElse(null);

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setDescription(user.getDescription());

        return userRepository.save(existing);
    }

    //To delete a user by his id
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

}
