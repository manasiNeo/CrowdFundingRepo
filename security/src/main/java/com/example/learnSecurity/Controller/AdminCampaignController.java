package com.example.learnSecurity.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSecurity.model.Admin;
import com.example.learnSecurity.model.Campaign;
import com.example.learnSecurity.service.AdminService;
import com.example.learnSecurity.service.CampaignService;


@RestController
@RequestMapping("/admin/{adminId}/campaigns")
public class AdminCampaignController {
    
    
    private final AdminService adminService;
    private final CampaignService campaignService;

    @Autowired
    public AdminCampaignController(AdminService adminService, CampaignService campaignService) {
        this.adminService = adminService;
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaignsByAdminId(@PathVariable Integer adminId) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Campaign> campaigns = campaignService.getAllCampaignsByAdmin(admin);
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaignByAdminIdAndCampaignId(@PathVariable Integer adminId,
                                                                      @PathVariable Integer campaignId) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Campaign campaign = campaignService.getCampaignByAdminAndCampaignId(admin, campaignId);
        if (campaign == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }
}
