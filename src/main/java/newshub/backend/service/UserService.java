package newshub.backend.service;

import newshub.backend.model.User;
import newshub.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // === CREATE / Signup ===
    public String signup(User user) {
        // Check if email already exists
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already registered";
        }
        // Check if username already exists
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already taken";
        }
        userRepository.save(user);
        return "Signup successful";
    }

    // === LOGIN ===
    public String login(String emailOrUsername, String password) {
        // First try email
        Optional<User> userOpt = userRepository.findByEmail(emailOrUsername);

        // If email not found, try username
        if(userOpt.isEmpty()) {
            userOpt = userRepository.findByUsername(emailOrUsername);
        }

        if(userOpt.isPresent()) {
            User user = userOpt.get();
            if(user.getPassword().equals(password)) {  
                return "Login successful";
            } else {
                return "Incorrect password";
            }
        }

        return "User not found";
    }

    // === READ ALL ===
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // === READ by ID ===
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // === READ by Email ===
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // === READ by Username ===
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // === UPDATE ===
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // === DELETE ===
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


