package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.Enities.User;

public interface UserService {

    User saveUser(User user);
    User getUserById(String id);
    Optional<User> updatUser(User user);
    void  deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();
    User getUserByEmail(String email);


    
    
}
