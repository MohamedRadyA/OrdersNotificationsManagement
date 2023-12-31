package com.app.notifications;

import com.app.model.User;
import com.app.notifications.channel.Channel;
import com.app.notifications.channel.ChannelDecorator;
import com.app.repo.StatisticsDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class NotificationManager {
    Queue<Notification> notificationQueue;

    StatisticsDatabase database;

    @Autowired
    public NotificationManager(@Qualifier("inMemoryStatisticsDatabase") StatisticsDatabase database) {
        this.notificationQueue = new LinkedList<Notification>();
        this.database = database;
    }

    public void sendNotification(NotificationTemplate template, HashMap<String, Object> map, User user) {
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
