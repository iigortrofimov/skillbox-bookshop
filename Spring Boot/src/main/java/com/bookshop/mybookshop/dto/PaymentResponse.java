package com.bookshop.mybookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {

    private boolean isSuccess;
    private String message;
}
