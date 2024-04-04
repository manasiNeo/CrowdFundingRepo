package com.MilaapClone.MilaapClone.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MilaapClone.MilaapClone.Model.Campaign;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign, Long> {

} 