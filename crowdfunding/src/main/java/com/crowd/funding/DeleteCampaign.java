package com.crowd.funding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCampaign {
    private static final Scanner scanner = new Scanner(System.in);

    public static void deleteCampaign(Connection connection, int userId) throws SQLException {
        System.out.println("Campaigns going on!!!");
        System.out.println();
        String selectQuery = "SELECT campaignId, title FROM campaign WHERE userId = ?";
        boolean hasCampaigns = false;
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, userId);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    int campaignId = resultSet.getInt("campaignId");
                    String title = resultSet.getString("title");
                    System.out.println("Campaign ID: " + campaignId + ", Title: " + title);
                    hasCampaigns = true;
                }
            }
        }

        if (!hasCampaigns) {
            System.out.println("You have no campaigns left to delete.");
            return;
        }
    
        System.out.print("Enter Campaign ID to delete: ");
        int campaignId = scanner.nextInt();
        scanner.nextLine(); 

        String checkOwnershipQuery = "SELECT * FROM campaign WHERE campaignId = ? AND userId = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkOwnershipQuery)) {
            checkStatement.setInt(1, campaignId);
            checkStatement.setInt(2, userId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    String deleteQuery = "DELETE FROM campaign WHERE campaignId = ?";
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                        deleteStatement.setInt(1, campaignId);
                        int rowsAffected = deleteStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Campaign deleted successfully.");
                        } else {
                            System.out.println("Failed to delete campaign.");
                        }
                    }
                } else {
                    System.out.println("Campaign not found or you don't have permission to delete it.");
                }
            }
        }
        
    }
}
