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
        return dateTimeParser.parse(date.text().split(" \\[")[0]);
    }

    private String parseTitle(Document doc) {
        Elements row = doc.select(".messageHeader");
        return row.get(0).text().replace(" [new]", "");
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            try {
                Element href = td.child(0);
                Post post = detail(href.attr("href"));
                posts.add(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Post post = new Post();
        post.setLink(link);
        post.setDescription(parseDescription(doc));
        post.setCreated(parseDateOfCreationPost(doc));
        post.setTitle(parseTitle(doc));
        return post;
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse parser = new SqlRuParse(new SqlRuDateTimeParser());
        List<Post> posts = parser.list("https://www.sql.ru/forum/job-offers");
        for (Post post : posts) {
            System.out.println(post);
        }
    }

}
