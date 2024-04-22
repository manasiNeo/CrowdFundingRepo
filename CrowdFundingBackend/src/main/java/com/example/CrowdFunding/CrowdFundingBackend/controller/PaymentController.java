package com.example.CrowdFunding.CrowdFundingBackend.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CrowdFunding.CrowdFundingBackend.model.Payment;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.PaymentServiceImplementation;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
   
    @Autowired
    private PaymentServiceImplementation paymentServiceImplementation;

    // Getting all the payment details.
    @GetMapping
    public ResponseEntity<Iterable<Payment>> getAllPayments() {
        try {
            Iterable<Payment> payment = paymentServiceImplementation.getAllPayments();
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // getting payment detaisl by the id
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        try {
            // get the payment by id
            Optional<Payment> payment = paymentServiceImplementation.getPaymentById(id);
            return payment.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        
    }

    // User makes the payment.
    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody Payment payment) {
        try {
            // Save the payment
            Payment savedPayment = paymentServiceImplementation.createPayment(payment);

            if(savedPayment != null) {
                ResponseEntity<String> ok = ResponseEntity.ok("Payment Successful.");
                return ok;
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

}
