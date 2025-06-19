package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.helper.AppConstants;
import com.example.demo.helper.ResourceNotFoundException;
import com.example.demo.Enities.User;
import com.example.demo.repsitiories.UserRepo;
import com.example.demo.services.UserService;

// import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        // password encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set the user role

        user.setRoleList(List.of(AppConstants.ROLE_USER));

        //
        logger.info(user.getProvider().toString());
        return userRepo.save(user);
        // TODO Auto-generated method stub
    }

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<User> updatUser(User user) {

        User user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        // user2.setContacts(user.getContacts());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setEnabled(user.isEnabled());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    // @Override
    // public Optional<User> deleteUser(String id) {
    // User user2 = userRepo.findById(id).orElseThrow(()-> new
    // ResourceNotFoundException("User not found"));
    // return userRepo.delete(user2);
    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepo.delete(user2);

    
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
