package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private String parseDescription(Document doc) {
        Elements row = doc.select(".msgBody");
        return row.get(1).text();
    }

    private LocalDateTime parseDateOfCreationPost(Document doc) {
        Element date = doc.select(".msgFooter").first();
        return new SqlRuDateTimeParser().parse(date.text().split(" \\[")[0]);
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            Post post = new Post();
            post.setLink(href.attr("href"));
            post.setTitle(href.text());
            posts.add(post);
        }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Post post = new Post();
        post.setDescription(parseDescription(doc));
        post.setCreated(parseDateOfCreationPost(doc));
        return post;
    }

    public static void main(String[] args) throws IOException {
//        int pagesCount = 0;
//        for (int currentPage = 1; currentPage <= pagesCount; currentPage++) {
//            Document doc = Jsoup.connect(String.format(
//                    "https://www.sql.ru/forum/job-offers/%s", currentPage)).get();
//            Elements row = doc.select(".postslisttopic");
//            for (Element td : row) {
//                Element href = td.child(0);
//                System.out.println(href.attr("href"));
//                System.out.println(href.text());
//                System.out.println(td.nextElementSiblings().get(3).text());
//            }
//        }

//        String href = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
//        Document doc = Jsoup.connect(href).get();
//        System.out.println(getDescription(doc));
//        System.out.println(getDateOfCreationPost(doc));
    }

}
