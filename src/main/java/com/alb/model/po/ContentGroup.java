package com.alb.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс для доступа к таблице Группа единиц контента.
 * 
 * @author Альберт
 */

public class ContentGroup {
    @Getter @Setter
    private long id;

    @Getter @Setter
    private String name;
}