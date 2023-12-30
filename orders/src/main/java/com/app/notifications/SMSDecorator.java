package com.app.notifications;

public class SMSDecorator extends ChannelDecorator{

    public SMSDecorator(Channel wrappee, String address) {
        super(wrappee, address);
    }

    @Override
    public void send(String message) {

    }

}
