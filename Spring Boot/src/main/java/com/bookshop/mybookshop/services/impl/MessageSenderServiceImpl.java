package com.bookshop.mybookshop.services.impl;

import com.bookshop.mybookshop.domain.SmsCode;
import com.bookshop.mybookshop.security.SmsService;
import com.bookshop.mybookshop.services.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSenderServiceImpl implements MessageSenderService {

    private final SmsService smsService;

    private final JavaMailSender mailSender;

    @Override
    public void sendCodeViaEmail(String to, String subject) {
        SmsCode code = new SmsCode(smsService.generateCode(), 300);
        smsService.saveNewCode(code);
        sendMessageViaEmail(to, subject, "Verification code: " + code.getCode());
    }

    @Override
    public void sendMessageViaEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("special-mail-for-dev@mail.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
