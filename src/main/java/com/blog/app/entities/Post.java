package com.blog.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = false, length = 10000)
    private String content;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
