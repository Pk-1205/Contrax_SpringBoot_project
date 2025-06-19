package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageSevice {

    String uploadImage(MultipartFile picture);

    String getUrlFromPublicId(String publicId);

    
}


