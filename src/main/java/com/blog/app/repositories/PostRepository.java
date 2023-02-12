package com.blog.app.repositories;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    //Custom finder method
    Page findByUser(User user, Pageable pageable);

    //Custom finder method
    List<Post> findByUser(User user);

    //Custom finder method
    Page findByCategory(Category category, Pageable pageable);

    //Custom finder method
    List<Post> findByCategory(Category category);

    //Custom search method
    Page searchByTitleContaining(String keyword, Pageable pageable);
    
}
