package ru.job4j.grabber;

import java.time.LocalDateTime;
/**
 * Модель данных, представляющая собой ссылку на вакансию
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
}
