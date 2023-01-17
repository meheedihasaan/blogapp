package com.blog.app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

}
