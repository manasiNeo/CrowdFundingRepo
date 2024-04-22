package com.example.CrowdFunding.CrowdFundingBackend.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CrowdFunding.CrowdFundingBackend.model.Admin;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.AdminServiceImplementation;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

        
    @Autowired
    private AdminServiceImplementation adminServiceImplementation;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try{
            Admin createdAdmin = adminServiceImplementation.saveAdmin(admin);
            return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            List<Admin> admins = adminServiceImplementation.getAllAdmins();
            return new ResponseEntity<>(admins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Integer adminId) {
        Admin admin = adminServiceImplementation.getAdminById(adminId);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
