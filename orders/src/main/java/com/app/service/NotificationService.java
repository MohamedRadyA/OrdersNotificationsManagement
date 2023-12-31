package com.app.service;

import com.app.model.notification.Notification;
import com.app.model.notification.NotificationTemplate;
import com.app.model.User;
import com.app.model.channel.Channel;
import com.app.model.channel.ChannelDecorator;
import com.app.repo.StatisticsDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationService {
    Queue<Notification> notificationQueue;

    StatisticsDatabase database;

    @Autowired
    public NotificationService(@Qualifier("inMemoryStatisticsDatabase") StatisticsDatabase database) {
        this.notificationQueue = new LinkedList<Notification>();
        this.database = database;
    }

    public void sendNotification(NotificationTemplate template, Map<String, String> map, User user) {
        String content = template.parseTemplate(map);
        Channel channel = user.getChannel();
        addNotification(content, channel, new Date(System.currentTimeMillis()));
        database.incrementTemplateCounter(template);
        while (channel instanceof ChannelDecorator) {
            database.incrementAddressCounter(((ChannelDecorator) channel).getAddress());
            channel = ((ChannelDecorator) channel).getWrappee();
        }
    }

    public void addNotification(String content, Channel channel, Date date) {
        Notification notification = new Notification(content, channel, date);
        this.notificationQueue.add(notification);
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    public void notificationCleanup() {
        if (!notificationQueue.isEmpty()) {
            Notification notification = notificationQueue.poll();
            notification.getChannel().send(notification.getContent());
        }
    }
}
