package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {

    SmsCode findByCode(String code);
}
