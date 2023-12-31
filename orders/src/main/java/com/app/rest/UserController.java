package com.app.rest;

import com.app.auth.Constants;
import com.app.model.User;
import com.app.repo.Database;
import com.app.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (!userService.loginUser(user)) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(userService.generateUserToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (!userService.createUser(user)) {
            return ResponseEntity.badRequest().body("User creation failed");
        }
        return ResponseEntity.ok().body(userService.generateUserToken(user));
    }

    @PutMapping("/setchannels/{username}")
    public ResponseEntity<String> setChannel(@PathVariable String username, @RequestBody Map<String, Boolean> channels) {
        if (!userService.setChannel(username, channels)) {
            return ResponseEntity.badRequest().body("Channels not set");
        }
        return ResponseEntity.ok().body("Channels set successfully");
    }


}
