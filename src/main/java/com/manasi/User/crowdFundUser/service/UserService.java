package com.manasi.User.crowdFundUser.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manasi.User.crowdFundUser.model.User;
import com.manasi.User.crowdFundUser.repository.UserRepository;

@Service
public class UserService {
    
    //Connecting the service layer to repository layer
    @Autowired
    private UserRepository userRepository;

    //To get all the user details. like select * from users;
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //To get userdetails by their id
    public Optional<User> getUsersById(int userId){
        return userRepository.findById(userId);
    }

    //To insert details into user entity
    public User createUser(User user){

        // To validate the contact number
        if (!isContactValid(user.getContact())) {
            throw new IllegalArgumentException("Invalid contact format");
        }

        // To validate the email address.
        if (!isEmailValid(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // To check if the email address is already present or not
        if (isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }

    //To update user details by userId.
    public User updateUser(int userId, User newUser) {
        Optional<User> existingUserById = userRepository.findById(userId);
        if (existingUserById.isPresent()) {
            User existingUser = existingUserById.get();
            existingUser.setFirstName(newUser.getFirstName());
            existingUser.setLastName(newUser.getLastName());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPassword(newUser.getPassword());
            existingUser.setContact(newUser.getContact());
            existingUser.setContributionAmount(newUser.getContributionAmount());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }
    

    // Deleteing the user details by userId using deleteById()
    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

    private boolean isEmailValid(String email) {
        // Regular expression pattern for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
        // Compile the pattern into a regular expression object
        Pattern pattern = Pattern.compile(emailRegex);
        
        // Match the input email against the pattern
        Matcher matcher = pattern.matcher(email);
        
        // Return true if the email matches the pattern, indicating it's valid
        return matcher.matches();
    }

    private boolean isEmailExists(String email) {
        //Return true if email address already exisits
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    private boolean isContactValid(String contact) {
        // Check if contact number is null or empty or not a 9 digit number
        if (contact == null || contact.isEmpty() || contact.length() != 9) {
            return false; // Contact number is null or empty
        }
    
        // Check if contact number contains only digits
        for (char c : contact.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; 
            }
        }
        // All validation checks passed, contact number is valid
        return true;
    }
    

}
