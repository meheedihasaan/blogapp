package com.blog.app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    //To upload post image
    public String uploadImage(MultipartFile imageFile) throws IOException;

    //To serve post image
    public InputStream serveImage(String path, String imageName) throws FileNotFoundException;

}
