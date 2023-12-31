package com.app.rest;

import com.app.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RequestMapping("/user/")
public class UserController {

    @PostMapping("/login/")
    public String login() {
        return "Login";
    }

    @PostMapping("/register/")
    public String createUser() {
        return "Register";
    }

    @PostMapping("/setchannel/")
    public String setChannel() {
        return "Set channel";
    }

    public HashMap<String, String> generateToken(User user) {
        HashMap<String, String> token = new HashMap<>();
        token.put("token", "token");
        token.put("refreshToken", "refreshToken");
        return token;
        //TODO: Implement JWT
    }
}
