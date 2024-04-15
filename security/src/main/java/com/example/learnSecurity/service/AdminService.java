package com.example.learnSecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.learnSecurity.Repository.AdminRepository;
import com.example.learnSecurity.model.Admin;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Integer adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }
}
