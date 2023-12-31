package com.app.repo;

import com.app.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserDatabase implements UserDatabase {
    private Map<String, User> users;

    public InMemoryUserDatabase() {
        users = new HashMap<>();
    }

    @Override
    public Boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

}
