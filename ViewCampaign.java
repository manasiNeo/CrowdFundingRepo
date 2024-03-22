import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//C:\Users\shanm\IdeaProjects\Login\src
public class ViewCampaign {
    public static void viewCampaigns(Connection connection) throws SQLException {
        System.out.println("Campaigns:");
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT campaignId, title, SUBSTRING(description, 1, 40) AS short_description, target_amount, end_date, current_amount_raised, status FROM campaign";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (!resultSet.next()) {
                    System.out.println("Oops! Sorry, there are no campaigns available.");
                    return;
                }
    
                // Print table header
                System.out.printf("%-10s %-20s %-40s %-15s %-15s %-25s %-10s%n",
                                  "CampaignID", "Title", "Description", "Target Amount", "End Date",
                                  "Current Amount Raised", "Status");
                // Print table rows
                do {
                    int campaignId = resultSet.getInt("campaignId");
                    String title = resultSet.getString("title");
                    String shortDescription = resultSet.getString("short_description");
                    double targetAmount = resultSet.getDouble("target_amount");
                    String endDate = resultSet.getString("end_date");
                    double currentAmountRaised = resultSet.getDouble("current_amount_raised");
                    String status = resultSet.getString("status");
    
                    System.out.printf("%-10d %-20s %-40s %-15.2f %-15s %-25.2f %-10s%n",
                                      campaignId, title, shortDescription, targetAmount, endDate,
                                      currentAmountRaised, status);
                } while (resultSet.next());
            }
        }
    }
}
