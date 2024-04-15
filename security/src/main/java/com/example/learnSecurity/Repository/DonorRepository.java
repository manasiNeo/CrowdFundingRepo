package com.example.learnSecurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learnSecurity.model.Donor;

@Repository
public interface  DonorRepository extends  JpaRepository<Donor, Integer>{
    
    public Optional<Donor> findByEmail(String email);
    
}
