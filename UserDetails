package com.crowd.funding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDetails {
    public static void displayUsers(Connection connection) throws SQLException {
        System.out.println();
        System.out.println("See the user details");
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM users";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Print table header
                System.out.printf("%-10s %-20s %-20s %-15s%n",
                                  "UserID", "Name", "Contact Number", "Contribution Amount");
                while (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String name = resultSet.getString("name");
                    String contactNumber = resultSet.getString("contact_number");
                    double contributionAmount = resultSet.getDouble("contribution_amount");

                    System.out.printf("%-10d %-20s %-20s %-15s%n",
                                      userId, name, contactNumber, contributionAmount);
                }
            }
        }
    }
}
