package com.example.demo.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Enities.User;
import com.example.demo.Enities.providers;
import com.example.demo.Forms.UserForm;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String indexRedirect() {
    return "redirect:/home"; // or return "home" if you want to directly serve
    
    }

    @Controller
    public class MainController {

        @GetMapping("/signin")
        public String loginPage() {
            return "login"; // This should match a template like login.html
        }
    }

    // about
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("This is About Page");
        return "about";
    }

    // srvice
    @RequestMapping("/service")
    public String servicePage() {
        System.out.println("This is Service Page");
        return "services";
    }

    @RequestMapping("/home")
    public String home(Model model) {

        System.out.println(" Home page handler");
        model.addAttribute("name", "Hii this is Moddel Attributes");
        model.addAttribute("Youtube", " this is Youtube ");
        return "home";
    }

    // contact
    @RequestMapping("/contact")
    public String contact() {

        return new String("contact");
    }

   // login post
    // @RequestMapping("/login")
    // public String login() {

    //     return new String("login");
    // }
  //  login get
     @GetMapping("/login")
    public String login(){
        return new String("login");
    }

    // register
    @RequestMapping("/signup")
    public String signup(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "signup";
    }

    // processing register

    // @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    // public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
    //         HttpSession session) {

    //     System.out.println("signup register");

    //     // fetch form data
    //     System.out.println(userForm);
    //     // validation from data
    //     if (rBindingResult.hasErrors()) {
    //         return "signup";

    //     }

    //     // save to database
    //     // userservice
    //     // User user = User.builder()
    //     // .name(userForm.getName())
    //     // .about(userForm.getAbout())
    //     // .email(userForm.getEmail())
    //     // // .contacts(userForm.getContacts())
    //     // .password(userForm.getPassword())
    //     // .phoneNumber(userForm.getPhoneNumber())
    //     // .profilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg")

    //     // .build();
    //     User user = new User();
        
    //     user.setName(userForm.getName());
    //     user.setAbout(userForm.getAbout());
    //     user.setEmail(userForm.getEmail());
    //     // user.setContacts(userForm.getContacts());
    //     user.setPassword(userForm.getPassword());
    //     user.setPhoneNumber(userForm.getPhoneNumber());
    //     user.setProfilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg");
    //     user.setProvider(providers.SELF);

    //     User savedUser = userService.saveUser(user);
    //     System.out.println("user saves");
    //     // message = register successful
    //     // Message message = Message.builder().content("Registration
    //     // Scussfull").type(MessageType.green).build();
    //     session.setAttribute("message", "Registration Sucessful");

    //     // return msg
    //     return "redirect:/signup";
    // }



    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
                              HttpSession session) {
    System.out.println("signup register");
    System.out.println(userForm);

    // Validation error in form
    if (rBindingResult.hasErrors()) {
        return "signup";
    }

    // ✅ Check if email already exists
    if (userService.getUserByEmail(userForm.getEmail()) != null) {
        session.setAttribute("message", "Email is already registered. Please log in or use another email.");
        return "redirect:/signup";
    }

    // Build and save user
    User user = new User();
    user.setName(userForm.getName());
    user.setAbout(userForm.getAbout());
    user.setEmail(userForm.getEmail());
    user.setPassword(userForm.getPassword());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setProfilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg");
    user.setProvider(providers.SELF);

    try {
        User savedUser = userService.saveUser(user);
        System.out.println("User saved: " + savedUser.getEmail());
        session.setAttribute("message", "Registration Successful. Please login.");
    } catch (Exception e) {
        e.printStackTrace();
        session.setAttribute("message", "Registration failed. Try again later.");
        return "redirect:/signup";
    }

    return "redirect:/login";  // ✅ Go to login after success
}


}