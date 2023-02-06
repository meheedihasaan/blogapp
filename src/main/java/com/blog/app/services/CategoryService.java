package com.blog.app.services;

import com.blog.app.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //To create a category
    public CategoryDto createCategory(CategoryDto categoryDto);

    //To get all the categories
    public List<CategoryDto> getAllCategories();

    //To get a category by its id
    public CategoryDto getCategoryByID(int id);

    //To update a category
    public CategoryDto updateCategory(CategoryDto categoryDto, int id);

    //To delete a category by its id
    public void deleteCategory(int id);

    //To get a category by its title
    public CategoryDto getCategoryByTitleIgnoreCase(String title);

}
