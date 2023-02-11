package com.blog.app.repositories;

import com.blog.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //To get a category by its title
    Optional<Category> findByTitleIgnoreCase(String title);

}
