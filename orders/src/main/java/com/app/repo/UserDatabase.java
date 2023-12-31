package com.app.repo;

import com.app.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDatabase {

    Boolean userExists(String username);


    void addUser(User user);


    User getUser(String username);

}
