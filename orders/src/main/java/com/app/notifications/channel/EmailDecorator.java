package com.app.notifications.channel;

public class EmailDecorator extends ChannelDecorator{

    public EmailDecorator(Channel wrappee, String address) {
        super(wrappee, address);
    }

    public EmailDecorator() {
        super(null, null);
    }

    @Override
    public void send(String message) {
        super.send(message);
        // Simulate sending to address
    }
}
