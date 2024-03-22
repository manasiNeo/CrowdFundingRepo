package com.crowd.funding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.crowd.funding.Model.User;

public class Main 
{
    public static final Scanner scanner = new Scanner(System.in);

    private static Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/User";
        String username = "root";
        String password = "examly";
        return DriverManager.getConnection(url, username, password);

    }

    private static void viewUserDetails() {
        System.out.println("Enter User Name to View Details:");
        String name = scanner.nextLine();
    
        try {
          User existingUser = getUserByName(name);
          if (existingUser != null) {
            System.out.println("User ID: " + existingUser.getUserId());
            System.out.println("Name: " + existingUser.getName());
            System.out.println("Contact Number: " + existingUser.getContactNumber());
          } else {
            System.out.println("User not found.");
          }
        } catch (Exception e) { }
    }

    private static User getUserByName(String name) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
    
        try {
          con = getConnection();
          String query = "SELECT * FROM users WHERE name = ?";
          statement = con.prepareStatement(query);
          statement.setString(1, name);
          resultSet = statement.executeQuery();
    
          if (resultSet.next()) {
            user = new User();
            user.setUserId(resultSet.getInt("userId"));
            user.setName(resultSet.getString("name"));
            user.setContactNumber(resultSet.getString("contactNumber"));
          }
        } catch (Exception e) {}
        return user;
    }  

    private static void createUser(User user){
        Connection con = null;
        PreparedStatement st = null;
    
        try {
          con = getConnection();
          String query = "INSERT INTO users (name, contactNumber) VALUES (?, ?)";
          st = con.prepareStatement(query);
          st.setString(1, user.getName());
          st.setString(2, user.getContactNumber());
          st.executeUpdate();
          System.out.println("User created successfully!");
        } catch (Exception e) {}
    }

    private static void handleCreateUser() {

        System.out.println("Enter user Id");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter User Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Contact Number:");
        String contact = scanner.nextLine();
    
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setContactNumber(contact);
        try {
          createUser(user);
        } catch (Exception e) { }
    }


    public static void main( String[] args )
    {
        
        Connection connection = getConnection();

        System.out.println("Welcome to CrowdFunding: Funding the future together");

        
        while(true){
            System.out.println("Select from below options");
            System.out.println("1: To view the ongoing campaigns");
            System.out.println("2: To donate for a cause");
            System.out.println("3: To view user details");
            System.out.println("4: To create user");
            System.out.println("5: To exit");

            
            System.out.println("Enter you choice");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1:
                    //viewCampaigns(connection);
                    System.out.println("view campaign");
                break;

                case 2:
                    //donateToCampaign(connection);
                    System.out.println("donate to campaign");
                    break;

                case 3:
                    viewUserDetails();
                    break;
                case 4:
                    handleCreateUser();
                    break;
                case 5:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

}
