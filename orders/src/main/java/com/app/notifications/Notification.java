package com.app.notifications;

import com.app.notifications.channel.Channel;

import java.util.Date;

public class Notification {
    String content;
    Date date;
    Channel channel;


    public Notification(String content, Channel channel, Date date) {
        this.content = content;
        this.channel = channel;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", channel=" + channel +
                '}';
    }
}
