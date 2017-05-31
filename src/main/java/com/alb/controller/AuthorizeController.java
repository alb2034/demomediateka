package com.alb.controller;

import com.alb.service.AccountService;
import com.alb.model.vo.SignInData;
import com.alb.web.VisitCounter;
import com.alb.model.vo.Role;
import com.alb.web.UserRole;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

/**
 * Контроллер для обработки запроса авторизации клиента.
 * @author Альберт
 */
@Controller
@Slf4j
@PropertySource("classpath:application.properties")
public class AuthorizeController {  

    @Autowired
    private UserRole userRole;
    
    @Autowired
    private AccountService accountServiceImpl;
    
    @Autowired
    private VisitCounter visitCounter;
    
    @Value("${spring.session-timeout:20}")
    private int sessionTimeout;
            
    /**
     * Принимает данные формы аутентификации и на основе их возвращает клиенту
     * ответ с HTTP-статусом. В случае успеха время жизни сессии ограничивается
     * значением, указанным свойством {@code spring.session-timeout} из файла
     * {@code application.properties} или {@code 20} секундами в случае 
     * отсутствия данного свойства. 
     *
     * @param signInData  данные аутентификации
     * 
     * @param session   для установки времени жизни сессии
     * 
     * @return  {@link  HttpStatus#OK} в случае успешной авторизации
     *          или {@link HttpStatus#UNAUTHORIZED} в противном случае.
     */
    @RequestMapping("/login")
    public ResponseEntity login(
            @ModelAttribute("userData") SignInData signInData, HttpSession session) {


        //уcтановить роль пользователя
        userRole.setRole(signInData, accountServiceImpl.getUser(signInData));
        HttpStatus httpStatus;
        //неверный логин и пароль
        if (userRole.isGuest()) { 
            httpStatus = HttpStatus.UNAUTHORIZED;
            log.info("Отказ в авторизации клиенту с логином: {} и паролем: {}",
                    signInData.getLogin(), signInData.getPassword() );
            log.info("UserRoleDefiner-{}", userRole);
        }
        //данные верны
        else {  
            httpStatus = HttpStatus.OK;
            visitCounter.increase();
            log.info("Клиент {} успешно авторизован как {}, "
                    + "сейчас на сайте: {}, всего посещений: {}", 
                    new Object[]{ 
                        userRole, userRole.getRole(), 
                        visitCounter.getCurrentCount(), 
                        visitCounter.getTotalCount()
                    });
        }
        
        //ограничить время сессии
        session.setMaxInactiveInterval(sessionTimeout);
        //запомнить роль в глобальной сессии для представления
        session.setAttribute("userRole", new Role(userRole.getRole()));
        //послать ответ запроса на авторизацию
        return new ResponseEntity(httpStatus);
    }
    
    /**
     * Принимает запрос клиента на выход из авторизации и очищает его сессию.
     * После чего перенаправляет на домашнюю страницу.
     * 
     * 
     * @param session   для очистки сессии
     * 
     * @return  имя представления домашней страницы
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {       
        
        session.invalidate();
        return "redirect:/";
    }
}