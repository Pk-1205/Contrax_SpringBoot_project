package com.example.demo.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class Helper {

    

    public static String getEmailOfLoggedInUser(Authentication authentication) {
    if (authentication == null) return null;

    if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
        Object principal = oauthToken.getPrincipal();
        if (principal instanceof DefaultOAuth2User oauthUser) {
            // email or login-based email
            String email = (String) oauthUser.getAttribute("email");
            if (email == null) {
                // fallback for GitHub
                email = oauthUser.getAttribute("login") + "@gmail.com";
            }
            return email;
        }
    }

    return authentication.getName(); // For normal login
}


    
    

    }
 
 
    

