package com.bookshop.mybookshop.security;

import com.bookshop.mybookshop.dao.SmsCodeRepository;
import com.bookshop.mybookshop.domain.SmsCode;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${twilio.ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${twilio.TOKEN_AUTH}")
    private String TOKEN_AUTH;

    @Value("${twilio.TWILIO_NUMBER}")
    private String TWILIO_NUMBER;

    private final SmsCodeRepository smsCodeRepository;

    public String sendSecretCodeSms(String contact) {
        Twilio.init(ACCOUNT_SID, TOKEN_AUTH);
        String formattedContact = contact.replaceAll("[()-]", "");
        String generatedCode = generateCode();

        Message.creator(
                new PhoneNumber(formattedContact),
                new PhoneNumber(TWILIO_NUMBER),
                "Your secret code is: " + generatedCode)
                .create();
        return generatedCode;
    }

    public String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 6) {
            sb.append(random.nextInt(9));
        }
        sb.insert(3, " ");
        return sb.toString();
    }

    public void saveNewCode(SmsCode code) {
        if (smsCodeRepository.findByCode(code.getCode()) == null) {
            smsCodeRepository.save(code);
        }
    }

    public Boolean virifyCode(String code) {
        SmsCode smsCode = smsCodeRepository.findByCode(code);
        return (smsCode != null && !smsCode.isExpired());
    }
}
