package br.com.tokunaga.adapter.datastore.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ConverteDataUtil {

    private static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected LocalDate convertToLocalDate(String dateString) {
        return LocalDate.parse(dateString, localDateFormatter);
    }
}
