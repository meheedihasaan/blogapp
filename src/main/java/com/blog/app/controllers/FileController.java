package com.blog.app.controllers;

import com.blog.app.payloads.PostDto;
import com.blog.app.services.FileService;
import com.blog.app.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/posts/")
public class FileController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")  //From application.properties file
    private String path;

    //To upload post image
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable int id) throws IOException {
        PostDto postDto = this.postService.getPostById(id);

        String imageName = this.fileService.uploadImage(path, image);
        postDto.setImageUrl(imageName);
        PostDto updatedPostDto = this.postService.updatePost(postDto, id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //To serve post image
    @GetMapping(value = "/downloadImage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.serveImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
