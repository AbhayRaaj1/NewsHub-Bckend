package newshub.backend.controller;

import newshub.backend.dto.LoginRequest;
import newshub.backend.model.User;
import newshub.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Login Successful! Welcome " + user.getUsername());
            } else {
                return ResponseEntity.status(401).body(" Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body(" User not found with email: " + loginRequest.getEmail());
        }
    }
}
