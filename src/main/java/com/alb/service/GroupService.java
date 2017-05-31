package com.alb.service;

import com.alb.model.po.ContentGroup;
import java.util.List;

/**
 * Описывает метод, возвращающий список наименований групп единиц контента из бд.
 * 
 * @author Альберт
 */
public interface GroupService {
    
    /**
     * Извлекает список наименований групп единиц контента из бд.
     * 
     * @return  список наименований групп единиц контента или пустой, если ничего не найдено.
     */
    List<ContentGroup> getAll();
}
