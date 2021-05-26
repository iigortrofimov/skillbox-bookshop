package com.bookshop.mybookshop.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sms_keys")
public class SmsCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private LocalDateTime expiredTime;

    public SmsCode(String code, Integer expireIn) {
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public SmsCode() {
    }

    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }
}
