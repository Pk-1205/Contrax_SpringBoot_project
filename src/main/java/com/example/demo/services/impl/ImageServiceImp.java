

package com.example.demo.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.helper.AppConstants;
import com.example.demo.services.ImageSevice;

@Service
public class ImageServiceImp implements ImageSevice {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            return null;
        }

        String publicId = UUID.randomUUID().toString(); // Unique ID

        try {
            // Upload the image to Cloudinary
            cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap(
                "public_id", publicId,
                "resource_type", "image",
                "overwrite", true
            ));

            // Return the transformed image URL
            return getUrlFromPublicId(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url()
            .transformation(new Transformation<>()
                .width(AppConstants.PICTURE_WIDTH)
                .height(AppConstants.PICTURE_HEIGHT)
                .crop(AppConstants.PICTURE_CROP)
            )
            .generate(publicId);
    }
}
