package com.example.croundFunding.model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CampaignModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int campaignId;
    private String title;
    private String description;
    private double goalAmount;
    private double raisedAmount;
    Date startDate;
    Date endDate;
    private double id;
    private String category;
    private double pendingAmount;
    private String status;
}
