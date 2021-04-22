package com.alb.controller;

import com.alb.model.vo.SignInData;
import com.alb.web.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Данный контроллер обрабатывает навигацию клиента на корневой и 
 * домашний адрес приложения.
 * 
 * @author Альберт
 */
@Controller
@Slf4j
public class WelcomeController {
    @Autowired
    private UserRole userRole;
    
    /**
     * Проверяет, является ли клиент запрос авторизованным. В случае успеха
     * возращает представление страницы со списком единиц контента. В противном случае,
     * представление домашней страницы с атрибутом модели {@link SignInData}
     * для заполнения данных для авторизации.
     * 
     * @return  представление 
     */
    @RequestMapping(path =  {"/welcome"})
    public ModelAndView welcome() {       
        
        if (userRole == null || userRole.isGuest()) {
            ModelAndView welcome = new ModelAndView("welcome");
            welcome.addObject("userData", new SignInData());
            
            log.info("Ожидание авторизации клиента {} на домашней странице.",
                    userRole);
            return welcome; 
        }
        log.info("Клиент {} уже авторизован, "
                + "перенаправление на страницу со списком.", userRole);
        return new ModelAndView("redirect:/content");
    }
}