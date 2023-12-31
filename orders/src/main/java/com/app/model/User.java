package com.app.model;

import com.app.notifications.Channel;

public class User {
    private String username;

    private String password;

    private String phoneNumber;

    private String emailAddress;

    private String preferredLang;

    private double balance;

    private Channel channel;
    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
    public User(String username, String password, double balance, String phoneNumber, String emailAddress, String preferredLang, Channel channel) {
        this(username, password, balance);
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.preferredLang = preferredLang;
        this.channel = channel;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPreferredLang(String preferredLang) {
        this.preferredLang = preferredLang;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPreferredLang() {
        return preferredLang;
    }

    public double getBalance() {
        return balance;
    }

    public Channel getChannel() {
        return channel;
    }

    public Boolean addBalance(double add){
        balance += add;
        return true;
    }

    public Boolean subtractBalance(double subtract){
        if(balance < subtract){
            return false;
        }
        balance -= subtract;
        return true;
    }
}