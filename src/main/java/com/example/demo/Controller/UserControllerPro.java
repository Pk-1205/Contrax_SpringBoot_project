package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.services.UserService;


@Controller
@RequestMapping("/user")
public class UserControllerPro {

    @Autowired
    private UserService userService;




    // user dashboard page

    @RequestMapping(value = "/dashboard")
    public String userdashboard() {
        return "user/dashboard";
    }

     @RequestMapping(value = "/profile")
    public String userProfile( Model model, Authentication authentication) 
    { 
       
        return "user/profile";
    }
    


    
}
