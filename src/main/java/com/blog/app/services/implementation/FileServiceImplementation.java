package com.blog.app.services.implementation;

import com.blog.app.services.FileService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {

    //To upload post image
    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String imageName = imageFile.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String randomImageName = randomId.concat(imageName.substring(imageName.lastIndexOf(".")));

        File path = new ClassPathResource("static/img").getFile();
        String filePath = path.getAbsolutePath() + File.separator + randomImageName;
        Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return randomImageName;
    }

    //To serve post image
    @Override
    public InputStream serveImage(String path, String imageName) throws FileNotFoundException {
        String fullPath = path + File.separator + imageName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

}
