import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/browseCampaign")
    public void browseCampaign(@PathVariable int userId) {
        userService.browseCampaign(userId);
    }

    @PostMapping("/{userId}/donateCampaign")
    public void donateCampaign(@PathVariable int userId, @RequestParam double amount) {
        userService.donateCampaign(userId, amount);
    }
}
