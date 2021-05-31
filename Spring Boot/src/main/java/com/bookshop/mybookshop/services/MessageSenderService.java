package com.bookshop.mybookshop.services;

public interface MessageSenderService {

    void sendCodeViaEmail(String to, String subject);

    void sendMessageViaEmail(String to, String subject, String text);
}
