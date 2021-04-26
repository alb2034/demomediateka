package com.alb.util.controller;

import java.util.HashMap;

/**
 * Утилита для хранения и быстрого поиска имен представлений,
 * используемых в приложении Видеотека.
 * 
 * @author Альберт
 */
public class ContentControllerPaths extends ControllerPaths {
    
    /**
     * Инициирует объект коллекцией имен представлений.
     */
    public ContentControllerPaths() {
        initPaths();
    }
    
    private void initPaths() {
        HashMap<String, String> pathMap = new HashMap<>();
        pathMap.put("/content", "content/content");
        pathMap.put("/card", "record_card/record_card");
        
        /*
        * родительский метод указывается с префиксом super, т.к. в случае его
        * переопределения, метод без префикса supper будет вызываться дочерний,
        * который в данном контексте еще не инициализирован
        */
        super.addPathMap(pathMap);
    }   
}