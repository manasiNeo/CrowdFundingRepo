package com.MilaapClone.MilaapClone.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MilaapClone.MilaapClone.Model.User;

public interface UserRepo extends JpaRepository<User, Long> {

    
}