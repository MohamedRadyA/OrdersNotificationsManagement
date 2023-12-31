package com.app.notifications;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.app.model.User;
import org.springframework.scheduling.annotation.Scheduled;

public class NotificationManager {
    Queue<Notification> notificationQueue;


    public NotificationManager() {
        this.notificationQueue = new LinkedList<Notification>();
    }

    public void sendNotification(NotificationTemplate template, HashMap<String, String> map, User user) {
        String content = template.parseTemplate(map);
        Channel channel = user.getChannel();
        channel.send(content);
    }

    public void addNotification(String content, Channel channel, Date date) {
        Notification notification = new Notification(content, channel, date);
        this.notificationQueue.add(notification);
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    public void notificationCleanup() {
        if (!notificationQueue.isEmpty()) {
            notificationQueue.poll();
        }
    }

}
