package com.example.demo.confi;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.Enities.User;
import com.example.demo.Enities.providers;
import com.example.demo.helper.AppConstants;
import com.example.demo.repsitiories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSucsess implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthenticationSucsess.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthenticationSucsess");

        // identify the user
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        User user = new User();
        user.setUserId((UUID.randomUUID().toString()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
                user.setPhoneNumber("OAUTH-" + UUID.randomUUID().toString().substring(0, 8));


        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(providers.GOOGLE);
            user.setAbout("This account is created using Google.");

            // google
            // goole attibutes

        }

        else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

            // github
            // github atributes

            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerId = oauthUser.getName();
            user.setEmail(email);
            user.setProfilePic(picture);
            user.setProviderUserId(providerId);
            user.setName(name);
            user.setProvider(providers.GITHUB);
            user.setAbout("This account is created using GitHub.");

        }

        else {
            logger.info("OAuthenticationSucsess : Unknown");
logger.warn("Unsupported provider: {}", authorizedClientRegistrationId);
            response.sendRedirect("/login?error=unsupported");
            return;

        }
        

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            logger.info("New user saved: {}", user.getEmail());

        }   else {
            logger.info("User already exists: {}",user.getEmail());
        }

        response.sendRedirect("/user/profile");

    }

}
