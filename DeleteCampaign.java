import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCampaign {
    private static final Scanner scanner = new Scanner(System.in);

    public static void deleteCampaign(Connection connection, int userId) throws SQLException {
        System.out.println("Your Campaigns:");
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
    
        // If the user has no campaigns, display appropriate message
        if (!hasCampaigns) {
            System.out.println("You have no campaigns left to delete.");
            return;
        }
    
        // Prompt for campaign selection
        System.out.print("Enter Campaign ID to delete: ");
        int campaignId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        // Check if the selected campaign exists and belongs to the logged-in user
        String checkOwnershipQuery = "SELECT * FROM campaign WHERE campaignId = ? AND userId = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkOwnershipQuery)) {
            checkStatement.setInt(1, campaignId);
            checkStatement.setInt(2, userId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Campaign found and belongs to the logged-in user, proceed with deletion
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
