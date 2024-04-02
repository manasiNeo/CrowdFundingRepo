import java.util.Date;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateJoined;
    private int contact;
    private double contributionAmount;

    public User() {
        // Default constructor
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public double getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(double contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    // Logic for browsing campaigns
    public void browseCampaign() {
        System.out.println("Browsing campaigns...");
        // Implement actual logic here, such as fetching campaigns from a database, API, or any other source.
        // Example:
        for (Campaign campaign : campaigns) {
            System.out.println(campaign);
        }
    }

    // Logic for donating to campaigns
    public void donateCampaign(double amount) {
        this.contributionAmount += amount;
        System.out.println("Donated $" + amount + " to a campaign.");
    }
}
