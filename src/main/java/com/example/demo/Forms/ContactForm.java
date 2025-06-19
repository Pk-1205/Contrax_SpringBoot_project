package com.example.demo.Forms;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp="^[0-9]{10}$", message = "invalid number")
    private String phoneNumber;
    private String address;
    private String about;
    private String webSiteLink;
    private String linkedInLink;

    @ValidFile(message = "Invalid File")
    private MultipartFile picture;
    private boolean favorite;
    private String pictureUrl;
    private UUID id; // For displaying the existing image


}
