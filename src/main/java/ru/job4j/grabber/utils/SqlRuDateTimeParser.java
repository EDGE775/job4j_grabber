package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Locale LOCALE = Locale.getDefault();

    @Override
    public LocalDateTime parse(String parse) {
        String date = parse.toLowerCase();
        String[] dateArray = date.split(" ");
        if (date.contains("сегодня")) {
            date = date.replace("сегодня", LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("d MMM yy", LOCALE)));
        } else if (date.contains("вчера")) {
            date = date.replace("вчера", LocalDate.now()
                    .minusDays(1)
                    .format(DateTimeFormatter.ofPattern("d MMM yy", LOCALE)));
        } else {
            String month = dateArray[1];
            month = month.equals("фев") ? "февр." : month.equals("сен") ? "сент."
                    : month.equals("ноя") ? "нояб." : month.equals("май") ? "мая"
                    : month.concat(".");
            dateArray[1] = month;
            date = String.join(" ", dateArray);
        }
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("d MMM yy, HH:mm", LOCALE);
        return LocalDateTime.parse(date, dtf);
    }

    public static void main(String[] args) {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        System.out.println(parser.parse("4 май 19, 21:19"));
    }
}