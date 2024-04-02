import java.util.Date;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Add any validation or business logic here before saving the user
        user.setDateJoined(new Date());
        return userRepository.save(user);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public void browseCampaign(int userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.browseCampaign();
        }
    }

    public void donateCampaign(int userId, double amount) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.donateCampaign(amount);
            userRepository.save(user);
        }
    }
}

