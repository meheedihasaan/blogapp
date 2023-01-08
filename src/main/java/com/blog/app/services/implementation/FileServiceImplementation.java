package com.blog.app.services.implementation;

import com.blog.app.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    //To upload post image
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String imageName = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String randomImageName = randomId.concat(imageName.substring(imageName.lastIndexOf(".")));

        String filePath = path+File.separator + randomImageName;

        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return randomImageName;
    }

    //To serve post image
    @Override
    public InputStream getResource(String path, String imageName) throws FileNotFoundException {
        String fullPath = path + File.separator + imageName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}
