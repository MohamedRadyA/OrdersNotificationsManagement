package com.app.notifications;

public class ChannelDecorator implements Channel {


    private Channel wrappee;
    private String address;

    public ChannelDecorator(Channel wrappee, String address) {
        this.wrappee = wrappee;
        this.address = address;
    }

    public Channel getWrappee() {
        return wrappee;
    }

    public String getAddress() {
        return address;
    }

    public void setWrappee(Channel wrappee) {
        this.wrappee = wrappee;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void send(String message) {

    }
}
