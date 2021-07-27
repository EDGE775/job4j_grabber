package ru.job4j.grabber;

import java.time.LocalDateTime;

/**
 * Модель данных, представляющая собой ссылку на вакансию
 *
 * @author Dmitry Khlapov
 */
public class Post {
    /**
     * идентификатор вакансии
     */
    private int id;
    /**
     * название вакансии
     */
    private String title;
    /**
     * ссылка на описание вакансии
     */
    private String link;
    /**
     * описание вакансии
     */
    private String description;
    /**
     * дата создания вакансии
     */
    private LocalDateTime created;

    public Post() {
    }

    public Post(int id, String title, String link,
                String description, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{"
                + "title='" + title + '\''
                + ", link='" + link + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + '}';
    }
}
