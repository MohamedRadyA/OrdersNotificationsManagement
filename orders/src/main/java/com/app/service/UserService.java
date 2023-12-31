package com.app.service;

import com.app.auth.Constants;
import com.app.model.User;
import com.app.notifications.channel.Channel;
import com.app.notifications.channel.ChannelDecorator;
import com.app.notifications.channel.ChannelFactory;
import com.app.notifications.channel.ConcreteChannel;
import com.app.repo.UserDatabase;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Map;

@Service
public class UserService {
    private final UserDatabase userDatabase;


    @Autowired
    public UserService(@Qualifier("inMemoryUserDatabase")UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }


    public Boolean createUser(User user) {
        if (userDatabase.userExists(user.getUsername()) || user.getPassword().isEmpty()) {
            return false;
        }
        userDatabase.addUser(user);
        return true;
    }
    public Boolean loginUser(User user){
        if (userDatabase.userExists(user.getUsername())) {
            return false;
        }
        User dbUser = userDatabase.getUser(user.getUsername());
        return dbUser.getPassword().equals(user.getPassword());
    }

    public Boolean setChannel(String username, Map<String, Boolean> channels) {
        if (!userDatabase.userExists(username)) {
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
        User user = userDatabase.getUser(username);
        user.setChannel(channel);
        return true;
    }

    public String generateUserToken(User user) {
        long time = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
                .setIssuedAt(new Date(time))
                .setExpiration(new Date(time + Constants.TOKEN_DURATION))
                .claim("username", user.getUsername())
                .compact();
        return token;
    }
}
