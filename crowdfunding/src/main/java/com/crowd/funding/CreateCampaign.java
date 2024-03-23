package com.crowd.funding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateCampaign {
    private static final Scanner scanner = new Scanner(System.in);

    public static void createCampaign(Connection connection, int userId) throws SQLException {
        System.out.println("Enter campaign details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Target Amount: ");
        double targetAmount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        String insertQuery = "INSERT INTO campaign (title, description, target_amount, end_date, status, userId) " +
                "VALUES (?, ?, ?, ?, 'active', ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setDouble(3, targetAmount);
            statement.setString(4, endDate);
            statement.setInt(5, userId); 
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Campaign created successfully.");
            } else {
                System.out.println("Failed to create campaign.");
            }
        }
        
    }
}
