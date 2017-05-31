package com.alb.controller;

import com.alb.web.UserRole;

import javax.servlet.http.*;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Spring-перехватчик для фильтрации запросов, требующих авторизацию
 * пользователя.
 */
@Slf4j
public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRole userRole;
    
    /**
     * Перенаправляет неавторизованные запросы на домашний адрес, кроме запросов
     * на адреса:<ul>
     * <li>/
     * <li>/authorize_status
     * <li>/login
     * <li>/logout
     * <li>/welcome</ul>
     * 
     * @param request
     * @param response
     * @param handler
     * @return {@code true}, если авторизован, иначе {@code false} 
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        if (userRole.isGuest()) {
            log.info("Клиент {} не авторизован, "
                    + "переход на домашнюю страницу.", userRole);
            response.sendRedirect(request.getContextPath());
            return false;
        }          
        return true;
    }
}