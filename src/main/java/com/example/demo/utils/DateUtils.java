package com.example.demo.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static String parseDateToString(java.util.Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(data);
    }

    public static Timestamp parseStringToTimestamp(String dataInicio) {
        if (dataInicio.isBlank()) return null;
        return Timestamp.valueOf(LocalDateTime.parse(dataInicio));
    }

    public static String parseTimestampToString(Timestamp data) {
        SimpleDateFormat formato = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss");
        return formato.format(data);
    }
}
