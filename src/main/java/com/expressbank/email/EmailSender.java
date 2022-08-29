package com.expressbank.email;

public interface EmailSender {

    void sendVerifyAccountLink(String to, String email);

    void sendNotifications(String to, String email);

}
