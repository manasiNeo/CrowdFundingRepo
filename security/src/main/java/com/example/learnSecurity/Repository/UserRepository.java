package com.example.learnSecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learnSecurity.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    //to find user by email as email is unique
    public User findByEmail(String email);
}
