package com.alb.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс для доступа к таблице Контент.
 * 
 * @author Альберт
 */
@NoArgsConstructor
public class Content {
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String author;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Long groupId;

    public Content(String name, String author) {
        this.name = name;
        this.author = author;
    }
}