public interface UserRepository {
    User findById(int userId);
    User save(User user);
}
