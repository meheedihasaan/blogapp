package com.blog.app.services.implementation;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CategoryDto;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    //To create a category
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepository.save(category);
        return categoryToDto(savedCategory);
    }

    //To get all the categories with list in blog-template
    public List<CategoryDto> getAllCategories(){
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoriesDto = categories
                                            .stream()
                                            .map((category)-> this.categoryToDto(category))
                                            .collect(Collectors.toList());

        return categoriesDto;
    }

    //To get all the categories with pagination in admin-panel
    @Override
    public Page<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        //To sort
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        else if (sortDirection.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        //For pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = this.categoryRepository.findAll(pageable);
        List<Category>  categories = page.getContent();
        List<CategoryDto> categoriesDto = categories
                                            .stream()
                                            .map((category)-> this.categoryToDto(category))
                                            .collect(Collectors.toList());

        return new PageImpl<CategoryDto>(categoriesDto, pageable, page.getTotalElements());
    }

    //To get a category by its id
    @Override
    public CategoryDto getCategoryByID(int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        return this.categoryToDto(category);
    }

    //To update a category
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = this.categoryRepository.save(category);
        return this.categoryToDto(updatedCategory);
    }

    //To delete a category by its id
    @Override
    public void deleteCategory(int id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        this.categoryRepository.delete(category);
    }

    //To get a category by its title
    @Override
    public CategoryDto getCategoryByTitleIgnoreCase(String title) {
        Optional<Category> categoryOptional = this.categoryRepository.findByTitleIgnoreCase(title);
        if(categoryOptional.isEmpty()){
            return null;
        }
        return this.categoryToDto(categoryOptional.get());
    }

    //To convert CategoryDto to Category
    public Category dtoToCategory(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }

    //To convert Category to CategoryDto
    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

}
