package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Locale LOCALE = Locale.getDefault();

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "янв."),
            Map.entry("фев", "февр."),
            Map.entry("мар", "мар."),
            Map.entry("апр", "апр."),
            Map.entry("май", "мая"),
            Map.entry("июн", "июн."),
            Map.entry("июл", "июл."),
            Map.entry("авг", "авг."),
            Map.entry("сен", "сент."),
            Map.entry("окт", "окт."),
            Map.entry("ноя", "нояб."),
            Map.entry("дек", "дек."));

    @Override
    public LocalDateTime parse(String parse) {
        String date = parse.toLowerCase();
        if (date.contains("сегодня")) {
            date = date.replace("сегодня", LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("d MMM yy", LOCALE)));
        } else if (date.contains("вчера")) {
            date = date.replace("вчера", LocalDate.now()
                    .minusDays(1)
                    .format(DateTimeFormatter.ofPattern("d MMM yy", LOCALE)));
        } else {
            String[] dateArray = date.split(" ");
            dateArray[1] = MONTHS.get(dateArray[1]);
            date = String.join(" ", dateArray);
        }
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("d MMM yy, HH:mm", LOCALE);
        return LocalDateTime.parse(date, dtf);
    }

    public static void main(String[] args) {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        System.out.println(parser.parse("4 Фев 19, 21:19"));
    }
}