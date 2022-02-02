package com.kafkaProcessing.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDate {

    public static String dateNow() {
        return DateTimeFormatter.ofPattern("HH:mm:ss.SSS").format( LocalDateTime.now() );
    }
}
