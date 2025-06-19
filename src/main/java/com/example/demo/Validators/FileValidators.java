package com.example.demo.Validators;



import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidators implements ConstraintValidator<ValidFile,MultipartFile>  {

   
    private static final long Max_File_Size = 1024*1024*5;
    // type

    //height

    // weight


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File can't be empty").addConstraintViolation();
            return false;

            
        }

        // file size

        if (file.getSize()>Max_File_Size) {
          context.disableDefaultConstraintViolation();
          context.buildConstraintViolationWithTemplate("File size should be less than 5MB").addConstraintViolation()  ;
          return false;
            
        }

        // resolution  if i want resulution then i can change here
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        //     if (bufferedImage.getHeight()) {
                
        //     }
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        return true;
       
    }

    
}
