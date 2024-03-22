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
            } else{
                System.out.println("User already present");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }

        return user;
    }

    private static void createUser(User user) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = getConnection();
            String query = "INSERT INTO users (name, contactNumber) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, user.getName());
            st.setString(2, user.getContactNumber());
            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }

    public static void getUserDetails() {
        try {
            System.out.println("Enter user name:");
            String name = scanner.nextLine(); // user name

            User user = getUserByName(name);
            if (user != null) {
                System.out.println("User ID: " + user.getUserId());
                System.out.println("Name: " + user.getName());
                System.out.println("Contact Number: " + user.getContactNumber());
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void makePayment() {
        try {
            
            String name = scanner.nextLine();        // user name
            String contact = scanner.nextLine();     // user contact

            User existingUser = getUserByName(name); // Create a new user if they don't exist
           
            if (existingUser == null) {
                User newUser = new User();
                newUser.setName(name);
                newUser.setContactNumber(contact);
                createUser(newUser);
                System.out.println("Welcome new user, " + name + "!");
            } else {
                System.out.println("Welcome back, " + name + "!");
            }

            System.out.print("Enter amount you want to donate: $");
            int donationAmount = scanner.nextInt();

            System.out.println("User " + name + " is donating $" + donationAmount + ".");

            
        } catch (SQLException e) { }
    }
    public static void main( String[] args )
    {
        System.out.println("Welcome to CrowdFunding: Funding the future together");
        System.out.println("Choose an option:");
        System.out.println("1. Make a Payment");
        System.out.println("2. View details");
        int input = scanner.nextInt();

        switch(input)
        {
            case 1:
                try{
                    makePayment();
                } catch(Exception e) {}
                break;
            case 2:
                try{
                    getUserDetails()
                }catch(Exception e){}
            default:
                System.out.println("Enter valid input");
                break;
        }

    }

}
