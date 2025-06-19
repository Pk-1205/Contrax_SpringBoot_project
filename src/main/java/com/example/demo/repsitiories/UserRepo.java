package com.example.demo.repsitiories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Enities.User;

public interface UserRepo extends JpaRepository<User, String> {





    Optional<User> findByEmail(String email);
}
