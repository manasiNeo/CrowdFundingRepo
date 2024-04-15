package com.example.learnSecurity.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSecurity.model.Campaign;
import com.example.learnSecurity.service.CampaignService;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    
    @Autowired
    private CampaignService campaignService;

    // Creating campaign
    @PostMapping
    public ResponseEntity<Campaign> saveCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign savedCampaign = campaignService.saveCampaign(campaign);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCampaign);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Getting details of all the campaign
    @GetMapping
    public ResponseEntity<Iterable<Campaign>> getAllCampaigns() {
        try {
            Iterable<Campaign> campaigns = campaignService.findAll();
            return ResponseEntity.ok(campaigns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Getting details of campaign by the id
    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable("campaignId") Integer id) {
        try {
            Optional<Campaign> campaign = campaignService.findById(id);
            return campaign.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Updating the campaign by id
    @PutMapping("/{campaignId}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable("campaignId") Integer id, @RequestBody Campaign campaignDetails) {
        try {
            Campaign updatedCampaign = campaignService.updateCampaign(id, campaignDetails);
            return ResponseEntity.ok(updatedCampaign);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Deleteing the campaign by id
    @DeleteMapping("/{campaignId}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable("campaignId") Integer id) {
        try {
            campaignService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/activeCampaigns")
    // Viewing the active campaign by the user
    public ResponseEntity<Iterable<Campaign>> getActiveCAmpaing(){

        try{
            Iterable<Campaign> activeCampaign = campaignService.findActiveCampaigns();
            return ResponseEntity.ok(activeCampaign);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
