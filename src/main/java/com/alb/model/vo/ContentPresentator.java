package com.alb.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс для передачи записей с единицами контента с соответствующими именами групп
 * и без детального описания.
 */
public class ContentPresentator {
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String author;

    @Getter @Setter
    private String group;
}