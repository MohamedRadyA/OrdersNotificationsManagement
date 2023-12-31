package com.app.notifications.channel;

public class EmailDecorator extends ChannelDecorator{

    public EmailDecorator(Channel wrappee, String address) {
        super(wrappee, address);
    }

    public EmailDecorator() {
        super(null, null);
    }

    public void send(String message) {

    }
}
