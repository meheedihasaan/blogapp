package com.blog.app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    //To upload post image
    public String uploadImage(String path, MultipartFile file) throws IOException;

    //To serve post image
    public InputStream getResource(String path, String imageName) throws FileNotFoundException;

}
