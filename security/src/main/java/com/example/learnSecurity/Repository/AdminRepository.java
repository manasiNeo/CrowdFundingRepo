package com.example.learnSecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learnSecurity.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{

    
}
