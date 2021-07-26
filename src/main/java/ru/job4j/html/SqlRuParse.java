package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        int pagesCount = 5;
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
    }
}
