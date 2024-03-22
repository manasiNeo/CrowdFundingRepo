import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class user {
    private static final Scanner scanner = new Scanner(System.in);

    public static int login(Connection connection) throws SQLException {
        System.out.print("Enter UserID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Check if user exists with given credentials
        String query = "SELECT * FROM users WHERE userId = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Login successful.");
                    return userId; // Return logged-in user ID
                } else {
                    System.out.println("Invalid UserID or Password.");
                    return -1; // Return -1 if login fails
                }
            }
        }

    }

    public static void signup(Connection connection) throws SQLException {
        System.out.print("Enter UserID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
    
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
    
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
    
        //Insert user details into the database
        String insertQuery = "INSERT INTO users (userId, name, password, contact_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setString(4, contactNumber);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User signed up successfully.");
            } else {
                System.out.println("Failed to sign up user.");
            }
        }
        
    }
}
