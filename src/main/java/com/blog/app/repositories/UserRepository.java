package com.blog.app.repositories;

import com.blog.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //To get user by username - For Authentication
    Optional<User> findByEmail(String email);

}
