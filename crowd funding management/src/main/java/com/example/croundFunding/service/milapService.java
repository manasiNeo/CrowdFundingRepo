package com.example.croundFunding.service;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.croundFunding.model.CampaignModel;
import com.example.croundFunding.repository.milapRepository;

@Service
public class milapService {
    @Autowired
    milapRepository repository;
   
   
    public CampaignModel milapSave(final CampaignModel entity){
        return repository.save(entity);
    }
    public Optional<CampaignModel> findById(final Integer aLong){
        return repository.findById(aLong);
    }
    public Iterable<CampaignModel> findAll(){
        return repository.findAll();
    }
    public void deleteById(final Integer shalik){
        repository.deleteById(shalik);
    }
    
}
