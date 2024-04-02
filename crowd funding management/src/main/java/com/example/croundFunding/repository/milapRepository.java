package com.example.croundFunding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.croundFunding.model.CampaignModel;

@Repository
public interface milapRepository extends JpaRepository<CampaignModel, Integer >{
}

