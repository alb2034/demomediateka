package com.alb.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс для подгрузки детального описания к контенту.
 */
@NoArgsConstructor
public class Description {
    @Getter @Setter
    private String description;
    
    /**
     * Инициализирует данный объект описанием, представленным в аргументе.
     * 
     * @param   description 
     *          описание
     */
    public Description(String description) {
        this.description = description;
    }
}