package com.app.rest;

import com.app.model.User;
import com.app.repo.Database;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
public class UserController {

    private UserService userService = UserService.getInstance();
    private Database database = Database.getInstance();

    @PostMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (!userService.createUser(user)) {
            return ResponseEntity.badRequest().body("User creation failed");
        }
        return ResponseEntity.ok().body("User created successfully");
    }

    @PutMapping("/setchannels/{username}")
    public ResponseEntity<String> setChannel(@PathVariable String username, @RequestBody Map<String, Boolean> channels) {
        if (!userService.setChannel(username, channels)) {
            return ResponseEntity.badRequest().body("Channels not set");
        }
        return ResponseEntity.ok().body("Channels set successfully");
    }

    public HashMap<String, String> generateToken(User user) {
        HashMap<String, String> token = new HashMap<>();
        token.put("token", "token");
        token.put("refreshToken", "refreshToken");
        return token;
        //TODO: Implement JWT
    }
}
