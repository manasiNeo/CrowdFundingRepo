package com.example.CrowdFunding.CrowdFundingBackend.controller;

import java.util.List;
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

import com.example.CrowdFunding.CrowdFundingBackend.model.Donor;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.DonorServiceimplementation;

@RestController
@RequestMapping("/api/donor")
public class DonorController {
    
    @Autowired
    private DonorServiceimplementation donorServiceImplementation;

    // To get all the users 
    @GetMapping()
    public ResponseEntity<List<Donor>> getAllDonors(){
        List<Donor> donors = donorServiceImplementation.getAllDonors();
        if(donors != null)
            return new ResponseEntity<>(donors, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    //To get users by id
    @GetMapping("/{donarId}")
    public ResponseEntity<Donor> getUserById(@PathVariable int userId){
        Optional<Donor> donorOptional = donorServiceImplementation.getDonorById(userId);
        if (donorOptional.isPresent()) {
            Donor donor = donorOptional.get();
            return ResponseEntity.ok().body(donor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //To insert data into user entity
    @PostMapping
    public ResponseEntity<Donor> createUser(@RequestBody Donor donor){
        Donor createdDonor = donorServiceImplementation.createDonor(donor);
        if(createdDonor !=null)
            return new ResponseEntity<>(createdDonor, HttpStatus.CREATED);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    //To update the user
    @PutMapping("/{donorId}")
    public ResponseEntity<Donor> updateDonor(@PathVariable int donorId, @RequestBody Donor donor){
        Donor updatedDonor = donorServiceImplementation.updateDonor(donorId, donor);
        if(updatedDonor != null)
            return new ResponseEntity<>(updatedDonor, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //To delete the user details with userId
    @DeleteMapping("/{donorId}")
    public ResponseEntity<Void> deleteDonor(@PathVariable int donorId){
        donorServiceImplementation.deleteDonor(donorId);
        return ResponseEntity.noContent().build();
    }
}
