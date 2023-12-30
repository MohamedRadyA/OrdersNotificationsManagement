package com.app.notifications;

import java.util.Date;

public class Notification {
    String content;
    Date date;
    Channel notification;


    public Notification(String content, Channel notification, Date date) {
        this.content = content;
        this.notification = notification;
        this.date = date;
    }
}
