package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.Enities.User;
import com.example.demo.helper.Helper;
import com.example.demo.services.UserService;

@ControllerAdvice
public class rootController {

    @Autowired
    private UserService userService;

 

//         @ModelAttribute
//     public void addLoggedInInformation(Model model,Authentication authentication){
//         if (authentication ==null) {
//             return;
            
//         }
//         System.out.println("aading logged in information into the user");
//          String name = Helper.getEmailOfLoggedInUser(authentication);

//         System.out.println("Usre Profile"+ name);
//         User user = userService.getUserByEmail(name);
//         // database se data ko feth  get user from db
//         System.out.println(user);
        
//             System.out.println(user.getName());
//         System.out.println(user.getEmail());
//         model.addAttribute("LoggedUser", user);


        
//     }

    
// }

@ModelAttribute
public void addLoggedInInformation(Model model, Authentication authentication) {
    if (authentication == null) {
        return;
    }

    System.out.println("Adding logged in information into the user");
    String email = Helper.getEmailOfLoggedInUser(authentication);
    System.out.println("User Profile email: " + email);

    User user = userService.getUserByEmail(email);

    if (user != null) {
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        model.addAttribute("LoggedUser", user);
    } else {
        System.out.println("User not found in DB for email: " + email);
        model.addAttribute("LoggedUser", null);
    }
}
}

