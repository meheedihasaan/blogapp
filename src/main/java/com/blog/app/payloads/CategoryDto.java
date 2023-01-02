package com.blog.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int id;

    @NotEmpty(message = "Title is required.")
    @Size(min = 4, max = 50, message = "Title must be between 4 to 50 characters.")
    private String title;

    @Size(min = 0, max = 1000, message = "Description must contain less than or equal to 1000 characters.")
    private String description;

}
