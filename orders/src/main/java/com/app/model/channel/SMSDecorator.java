package com.app.model.channel;

public class SMSDecorator extends ChannelDecorator{

    public SMSDecorator(Channel wrappee, String address) {
        super(wrappee, address);
    }

    public SMSDecorator() {
        super(null, null);
    }

    @Override
    public void send(String message) {
        super.send(message);
        // Simulate sending to address
    }

}
