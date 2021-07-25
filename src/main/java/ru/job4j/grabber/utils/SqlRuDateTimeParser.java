package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class SqlRuDateTimeParser implements DateTimeParser {

    private List<String> months = List.of("январь", "февраль", "март", "апрель",
            "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь");

    @Override
    public LocalDateTime parse(String parse) {
        String date = parse.toLowerCase();
        if (date.split(" ")[0].length() == 1) {
            date = "0".concat(date);
        }
        if (date.contains("сегодня")) {
            date = date.replace("сегодня", LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("dd LLLL yy")));
        } else if (date.contains("вчера")) {
            date = date.replace("вчера", LocalDate.now()
                    .minusDays(1)
                    .format(DateTimeFormatter.ofPattern("dd LLLL yy")));
        } else {
            for (int i = 0; i < months.size(); i++) {
                String month = date.split(" ")[1];
                if (months.get(i).contains(month)) {
                    date = date.replace(month, months.get(i));
                    break;
                }
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter
                .ofPattern("dd LLLL yy, HH:mm")
                .withLocale(new Locale("ru"));
        return LocalDateTime.parse(date, dtf);
    }
}