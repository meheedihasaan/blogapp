package com.blog.app.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int id;

    @NotEmpty(message = "Title is required.")
    @Size(min = 4, max = 50, message = "Title must be between 4 to 50 characters.")
    private String title;

    @NotEmpty(message = "Description is required.")
    @Size(min = 10, max = 1000, message = "Description should be between 10 to 1000 characters.")
    private String description;

}
