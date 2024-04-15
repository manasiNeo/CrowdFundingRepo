package com.example.learnSecurity.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learnSecurity.model.Admin;
import com.example.learnSecurity.model.Campaign;

@Repository
public interface  CampaignRepository extends JpaRepository<Campaign, Integer>{
    
    public List<Campaign>findByStatus(String status);
    public Optional<Campaign> findByTitle(String title);

    public List<Campaign> findByAdmin(Admin admin);

    public Campaign findByAdminAndCampaignId(Admin admin, Integer campaignId);
}
