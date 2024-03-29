package com.crowd.funding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewCampaign {
    public static void viewCampaigns(Connection connection) throws SQLException {
        System.out.println("See all the campaigns going on");
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT campaignId, title, description, target_amount, end_date, current_amount_raised, status FROM campaign";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (!resultSet.next()) {
                    System.out.println("There are no campaigns available.");
                    return;
                }

                System.out.printf("%-10s %-20s %-25s %-15s %-15s %-25s %-10s%n", "CampaignID", "Title", "Description", "Target Amount", "End Date", "Current Amount Raised", "Status");
                
                do {
                    int campaignId = resultSet.getInt("campaignId");
                    String title = resultSet.getString("title");
                    String shortDescription = resultSet.getString("description");
                    double targetAmount = resultSet.getDouble("target_amount");
                    String endDate = resultSet.getString("end_date");
                    double currentAmountRaised = resultSet.getDouble("current_amount_raised");
                    String status = resultSet.getString("status");
    
                    System.out.printf("%-10d %-20s %-25s %-20s %-20s %-25s %-10s%n",campaignId, title, shortDescription, targetAmount, endDate, currentAmountRaised, status);
                } while (resultSet.next());
                
            }catch(Exception e){}
            
        }catch (Exception e){}
    }
}
