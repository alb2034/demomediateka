package com.alb.util.controller;

import java.util.HashMap;

/**
 * Утилита для хранения и быстрого поиска имен представлений.
 * 
 * @author Альберт
 */
//@SuppressWarnings("Convert2Diamond")
public class ControllerPaths {
    private final HashMap<String, String> pathMap;

    /**
     * Инициализирует объект пустой коллекцией имен представлений и 
     * представлением по умолчанию - "index".
     */
    public ControllerPaths() {
        pathMap = new HashMap<>();     
    }
    
    /**
     * Вызывает {@link #ControllerPaths()} и инициализирует объект 
     * представлением по умолчанию в соответствии с аргументом.
     * 
     * @param   defaultViewName
     *          имя представления по умолчанию
     */
    public ControllerPaths(String defaultViewName) {
        this();
    }
    
    /**
     * Добавляет в коллекцию имен представлений коллекцию из аргумента.
     * 
     * @param   pathMap 
     *          коллекция имен представлений для добавления
     */
    public void addPathMap(HashMap<String, String> pathMap) {
        if (pathMap != null)
            this.pathMap.putAll(pathMap);
    }
    
    /** Возвращает имя представления по имени пути сервлета
     * если имя пути сервлета не найдено - null
     * 
     * @param   servletPath
     *          имени пути сервлета
     * 
     * @return  имя представления, соотвествующей {@code servletPath} либо
     *          null, если не найдено
     */
    public String getViewName(String servletPath) {
        return pathMap.get(servletPath);
    }   
}