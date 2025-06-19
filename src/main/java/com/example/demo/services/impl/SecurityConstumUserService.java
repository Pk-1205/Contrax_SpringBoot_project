package com.example.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;


import com.example.demo.repsitiories.UserRepo;

@Service
public class SecurityConstumUserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

   

   @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("Trying to login with username: " + username);
    
    return userRepo.findByEmail(username)
        .orElseThrow(() -> {
            System.out.println("User not found with email: " + username);
            return new UsernameNotFoundException("Invalid Username");
        });
}


    
}
