package com.example.learnSecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learnSecurity.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
