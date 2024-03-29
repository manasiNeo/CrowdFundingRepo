package com.crowd.funding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/milap";
    private static final String username = "root";
    private static final String password = "examly";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        
        Connection connection = DriverManager.getConnection(url, username, password);
        try(connection){
            System.out.println("         Welcome to CrowdFunding page !!!         ");
            System.out.println();

            boolean isLoggedIn = false;
            int userId = -1; 
            while (true) {
                if (isLoggedIn) {
                    System.out.println();
                    System.out.println("Selete from below options");
                    System.out.println("Enter 3 to create Campaign");
                    System.out.println("Enter 4 to donate to Campaign");
                    System.out.println("Enter 5 to delete a Campaign");
                    System.out.println("Enter 6 to viewing all the Campaigns going on");
                    System.out.println("Enter 7 to view user details");
                    System.out.println("Enter 8 to Logout");
                } else {
                    System.out.println();
                    System.out.println("Choose an option:");
                    System.out.println("Enter 1 to login");
                    System.out.println("Enter 2 for signup");
                    System.out.println("Enter 6 for viewing present campaign details");
                    System.out.println("Enter 8 for exiting the page");
                }

                System.out.println();
                System.out.print("Enter your choice: ");
                System.out.println();
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        userId = user.login(connection); 
                        isLoggedIn = userId != -1; 
                        break;
                    case 2:
                        user.signup(connection);
                        break; 
                    case 3:
                        if (isLoggedIn) 
                            CreateCampaign.createCampaign(connection, userId);
                        else
                            System.out.println("Need to Login");
                            System.out.println();
                        break;
                    case 4:
                        if (isLoggedIn) 
                           DonateToCampaign.donateToCampaign(connection, userId);
                        else
                            System.out.println("Need to Login");
                        break;
                    case 5:
                        if (isLoggedIn) 
                            DeleteCampaign.deleteCampaign(connection, userId);
                        else
                            System.out.println("Please login");
                        break;
                    case 6:
                        ViewCampaign.viewCampaigns(connection);
                        break;
                    case 7:
                        UserDetails.displayUsers(connection);
                        break;
                    case 8:
                        System.out.println();
                        System.out.println("Exiting the portal");
                        return;
                    default:
                        System.out.println();
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
