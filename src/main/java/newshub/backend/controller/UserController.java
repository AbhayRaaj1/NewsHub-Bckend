package newshub.backend.controller;

import newshub.backend.model.User;
import newshub.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5500")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // === SIGNUP ===
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        String result = userService.signup(user);
        if(result.equals("Signup successful")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // === LOGIN ===
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        String emailOrUsername = loginRequest.getEmail(); // frontend me email or username send
        String password = loginRequest.getPassword();

        String result = userService.login(emailOrUsername, password);
        if(result.equals("Login successful")) {
            return ResponseEntity.ok(result);
        } else if(result.equals("Incorrect password")) {
            return ResponseEntity.status(401).body(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    // === READ ALL USERS ===
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // === READ USER BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // === UPDATE USER ===
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === DELETE USER ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
