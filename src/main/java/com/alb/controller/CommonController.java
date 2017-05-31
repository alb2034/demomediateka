package com.alb.controller;

import com.alb.util.controller.ContentControllerPaths;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Данный контроллер обрабатывает навигацию клиента по адресам приложения,
 * отличных от корневого и домашнего.
 * @author Альберт
 */
@Controller
public class CommonController {
    private final ContentControllerPaths paths;
    
    /**
     * Инициализирует контроллер. Имя представления в случае отсутствия
     * искомого сервлета - "welcome"
     */
    public CommonController() {
        paths = new ContentControllerPaths("welcome");
    }
    
    /**
     * Возвращает имя представления соответствующего сервлета
     * @param request   для извлечения имени пути сервлета
     * @return  имя представления   
     */
    @RequestMapping("/*")
    public String content(HttpServletRequest request) {
        return paths.getViewName(request.getServletPath());
    }     
}