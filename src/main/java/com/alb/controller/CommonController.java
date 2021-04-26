package com.alb.controller;

import com.alb.util.controller.ContentControllerPaths;
import com.alb.web.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Данный контроллер обрабатывает навигацию клиента по адресам приложения,
 * отличных от корневого и домашнего.
 * @author Альберт
 */
@Controller
public class CommonController {

    @Autowired
    private UserRole userRole;

    private final ContentControllerPaths paths;
    
    /**
     * Инициализирует контроллер. Имя представления в случае отсутствия
     * искомого сервлета - "welcome"
     */
    public CommonController() {
        paths = new ContentControllerPaths();
    }
    
    /**
     * Возвращает имя представления соответствующего сервлета
     * @param request   для извлечения имени пути сервлета
     * @return  имя представления   
     */
    @RequestMapping("/*")
    public String content(HttpServletRequest request) throws IOException {
        String servletPath = request.getServletPath();
        String viewName = paths.getViewName(servletPath);

        if (viewName == null) {
            throw new FileNotFoundException(
                    String.format("Клиент %s запросил несуществующий ресурс %s", userRole, servletPath)
            );
        }

        return viewName;
    }     
}