package com.example.learnSecurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learnSecurity.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    //to find user by email as email is unique
    Optional<User> findByEmail(String email);
}
