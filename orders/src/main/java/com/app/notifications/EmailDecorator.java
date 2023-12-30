package com.app.notifications;

public class EmailDecorator extends ChannelDecorator{

    public EmailDecorator(Channel wrappee, String address) {
        super(wrappee, address);
    }

    public void send(String message) {

    }
}
