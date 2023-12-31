package com.app.service;

import com.app.model.User;
import com.app.notifications.*;
import com.app.repo.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class UserService {
    private final Database database;


    @Autowired
    public UserService(@Qualifier("inMemoryDatabase")Database database) {
        this.database = database;
    }


    public Boolean createUser(User user) {
        if (database.userExists(user.getUsername())) {
            return false;
        }
        database.addUser(user);
        return true;
    }

    public Boolean setChannel(String username, Map<String, Boolean> channels) {
        if (!database.userExists(username)) {
            return false;
        }
        Channel channel = new ConcreteChannel();
        for (var entry : channels.entrySet()) {
            if (!entry.getValue()) continue;
            ChannelDecorator tmpChannel = (ChannelDecorator) ChannelFactory.createChannel(entry.getKey());
            if(tmpChannel == null)continue;
            tmpChannel.setWrappee(channel);
            channel = tmpChannel;
        }
        User user = database.getUser(username);
        user.setChannel(channel);
        return true;
    }
}
