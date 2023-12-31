package com.app.service;

import com.app.auth.Constants;
import com.app.model.User;
import com.app.model.channel.Channel;
import com.app.model.channel.ChannelDecorator;
import com.app.model.channel.ChannelFactory;
import com.app.model.channel.ConcreteChannel;
import com.app.model.notification.NotificationTemplate;
import com.app.repo.UserDatabase;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Map;

import static java.util.Map.entry;

@Service
public class UserService {
    private final UserDatabase userDatabase;
    private final NotificationService notificationService;

    @Autowired
    public UserService(@Qualifier("inMemoryUserDatabase")UserDatabase userDatabase,
                       @Qualifier("notificationService") NotificationService notificationService) {
        this.userDatabase = userDatabase;
        this.notificationService = notificationService;
    }


    public Boolean createUser(User user) {
        if (userDatabase.userExists(user.getUsername()) || user.getPassword().isEmpty()) {
            return false;
        }
        userDatabase.addUser(user);
        Map<String, String> placeHolders = Map.ofEntries(entry("lang", user.getPreferredLang()),
                entry("name", user.getUsername()));
        notificationService.sendNotification(NotificationTemplate.USER_CREATION, placeHolders, user);
        return true;
    }
    public Boolean loginUser(User user){
        if (!userDatabase.userExists(user.getUsername())) {
            return false;
        }
        User dbUser = userDatabase.getUser(user.getUsername());
        if(dbUser.getPassword().equals(user.getPassword())){
            Map<String, String> placeHolders = Map.ofEntries(entry("lang", dbUser.getPreferredLang()),
                    entry("name", dbUser.getUsername()));
            notificationService.sendNotification(NotificationTemplate.USER_LOGIN, placeHolders, dbUser);
            return true;
        }
        return false;
    }

    public Boolean setChannel(String username, Map<String, Boolean> channels) {
        if (!userDatabase.userExists(username)) {
            return false;
        }
        Channel channel = new ConcreteChannel();

        for (Map.Entry<String, Boolean> entry: channels.entrySet()) {
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
