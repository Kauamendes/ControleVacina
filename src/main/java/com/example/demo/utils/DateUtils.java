package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {

    public static String getStringDataHoraAtual() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        Date dataAtual = Date.from(now.toInstant());
        return parseDateToString(dataAtual);
    }

    public static String parseDateToString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(data);
    }
}
