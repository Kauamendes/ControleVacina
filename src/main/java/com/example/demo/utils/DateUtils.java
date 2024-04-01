package com.example.demo.utils;

import java.text.SimpleDateFormat;

public class DateUtils {

    public static String parseDateToString(java.util.Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(data);
    }
}
