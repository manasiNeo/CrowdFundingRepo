package com.example.croundFunding.controller;
import java.util.Optional;
 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.croundFunding.model.CampaignModel;
import com.example.croundFunding.service.milapService;

@RestController
@RequestMapping("/api/home")
public class MilapController {
    private final milapService milapService;
 
    public MilapController(milapService milapService){
        this.milapService = milapService;
    }
 
    @PostMapping
    public CampaignModel saveCustomer(@RequestBody CampaignModel campaignModel){
        return milapService.milapSave(campaignModel);
    }
   
    @GetMapping
    public Iterable<CampaignModel> getAllCustomers(){
        return milapService.findAll();
    }
 
    @GetMapping("/{campaignId}")
    public Optional<CampaignModel> getCustomerById(@PathVariable("campaignId") Integer id){
        return milapService.findById(id);
    }
 
    @PutMapping("/{campaignId}")
    public CampaignModel updatCustomer(@RequestBody CampaignModel campaignModel){
        return milapService.milapSave(campaignModel);
    }
    @DeleteMapping("/{campaignId}")
    public void deleteCustomer(@PathVariable("campaignId") Integer id){
        milapService.deleteById(id);
    }
    
}
 