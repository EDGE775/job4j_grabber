package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {

    public static String getDescription(Document doc) {
        Elements row = doc.select(".msgBody");
        return row.get(1).text();
    }

    public static LocalDateTime getDateOfCreationPost(Document doc) {
        Element date = doc.select(".msgFooter").first();
        return new SqlRuDateTimeParser().parse(date.text().split(" \\[")[0]);
    }

    public static void main(String[] args) throws IOException {
        int pagesCount = 0;
        for (int currentPage = 1; currentPage <= pagesCount; currentPage++) {
            Document doc = Jsoup.connect(String.format(
                    "https://www.sql.ru/forum/job-offers/%s", currentPage)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(td.nextElementSiblings().get(3).text());
            }
        }

        String href = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        Document doc = Jsoup.connect(href).get();
        System.out.println(getDescription(doc));
        System.out.println(getDateOfCreationPost(doc));
    }
}
