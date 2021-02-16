package com.bookshop.mybookshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class DateTimeDto {

    private LocalDateTime dateTime;

    public DateTimeDto(String stringDate) {
        LocalDate parsedLocalDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.dateTime = LocalDateTime.of(parsedLocalDate, LocalTime.MIN);
    }
}
