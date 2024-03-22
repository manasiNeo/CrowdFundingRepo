import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DonateToCampaign {
    private static final Scanner scanner = new Scanner(System.in);

    public static void donateToCampaign(Connection connection, int userId) throws SQLException {

        
            boolean isEmpty = true;
    try (Statement emptyStatement = connection.createStatement()) {
        ResultSet emptyResultSet = emptyStatement.executeQuery("SELECT COUNT(*) AS total FROM campaign");
        if (emptyResultSet.next()) {
            int totalCount = emptyResultSet.getInt("total");
            if (totalCount > 0) {
                isEmpty = false;
            }
        }
    }

    // If the campaign table is empty, display the prompt and return
    if (isEmpty) {
        System.out.println("Oops! Sorry, you have no campaigns available to donate.");
        return;
    }

    // Display campaign details only once
    System.out.println("Campaigns:");
    try (Statement statement = connection.createStatement()) {
        String query = "SELECT campaignId, title, description, target_amount, end_date, current_amount_raised, status FROM campaign";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            // Print table header
            System.out.printf("%-10s %-20s %-40s %-15s %-15s %-25s %-10s%n",
                              "CampaignID", "Title", "Description", "Target Amount", "End Date",
                              "Current Amount Raised", "Status");
            // Print table rows
            while (resultSet.next()) {
                int campaignId = resultSet.getInt("campaignId");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                double targetAmount = resultSet.getDouble("target_amount");
                String endDate = resultSet.getString("end_date");
                double currentAmountRaised = resultSet.getDouble("current_amount_raised");
                String status = resultSet.getString("status");

                System.out.printf("%-10d %-20s %-40s %-15.2f %-15s %-25.2f %-10s%n",
                                  campaignId, title, description, targetAmount, endDate,
                                  currentAmountRaised, status);
            }
        }
    }

    // Ask for user input and proceed with donation
    System.out.print("Enter Campaign ID: ");
    int campaignId = scanner.nextInt();
    scanner.nextLine(); // Consume newline character

    // Check if the campaign is active
    String checkStatusQuery = "SELECT status FROM campaign WHERE campaignId = ?";
    try (PreparedStatement statusStatement = connection.prepareStatement(checkStatusQuery)) {
        statusStatement.setInt(1, campaignId);
        ResultSet statusResultSet = statusStatement.executeQuery();
        if (statusResultSet.next()) {
            String status = statusResultSet.getString("status");
            if (status.equals("active")) {
                // Proceed with donation
                System.out.print("Enter Donation Amount: ");
                double donationAmount = scanner.nextDouble();

                // Update campaign's current_amount_raised
                String updateCampaignQuery = "UPDATE campaign SET current_amount_raised = current_amount_raised + ? WHERE campaignId = ?";
                try (PreparedStatement campaignStatement = connection.prepareStatement(updateCampaignQuery)) {
                    campaignStatement.setDouble(1, donationAmount);
                    campaignStatement.setInt(2, campaignId);
                    int rowsAffected = campaignStatement.executeUpdate();

                    // Check if the campaign is completed
                    String checkCompletionQuery = "SELECT target_amount, current_amount_raised FROM campaign WHERE campaignId = ?";
                    try (PreparedStatement completionStatement = connection.prepareStatement(checkCompletionQuery)) {
                        completionStatement.setInt(1, campaignId);
                        ResultSet resultSet = completionStatement.executeQuery();
                        if (resultSet.next()) {
                            double targetAmount = resultSet.getDouble("target_amount");
                            double currentAmountRaised = resultSet.getDouble("current_amount_raised");
                            if (currentAmountRaised >= targetAmount) {
                                // Update campaign status to "completed"
                                String updateStatusQuery = "UPDATE campaign SET status = 'completed' WHERE campaignId = ?";
                                try (PreparedStatement statusUpdateStatement = connection.prepareStatement(updateStatusQuery)) {
                                    statusUpdateStatement.setInt(1, campaignId);
                                    statusUpdateStatement.executeUpdate();
                                }
                            }
                        }
                    }

                    // Update user's contribution_amount
                    if (rowsAffected > 0)
                    {
                        String updateUserQuery = "UPDATE users SET contribution_amount = contribution_amount + ? WHERE userId = ?";
                        try (PreparedStatement userStatement = connection.prepareStatement(updateUserQuery)) {
                            userStatement.setDouble(1, donationAmount);
                            userStatement.setInt(2, userId);
                            userStatement.executeUpdate();
                        }
                        System.out.println("Donation successful.");
                    } else {
                        System.out.println("Failed to donate to campaign. Campaign ID not found.");
                    }
                }
            } else {
                System.out.println("The campaign is closed/completed.");
            }
        } else {
            System.out.println("Campaign not found.");
        }
    }
        
        
    }
}
