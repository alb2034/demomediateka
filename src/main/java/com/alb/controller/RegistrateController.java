package com.alb.controller;

import com.alb.service.AccountService;
import com.alb.model.vo.SignInData;
import com.alb.web.UserRole;
import com.alb.web.VisitCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контроллер для обработки запроса регистрации клиента в системе.
 *
 * @author Альберт
 */
@Controller
@Slf4j
public class RegistrateController {

    @Autowired
    private UserRole userRole;
    
    @Autowired
    private AccountService accountServiceImpl;
    
    @Autowired
    private VisitCounter visitCounter;

    /**
     * Отправляет клиенту пустую форму с атрибутами для регистрации
     * нового пользователя в системе.
     *
     * @return  имя представления с формой регистрации
     */
    @RequestMapping("/signup")
    public ModelAndView signup() {

        if (userRole == null || userRole.isGuest()) {
            ModelAndView signup = new ModelAndView("signup/signup");
            signup.addObject("userData", new SignInData());

            log.info("Ожидание заполнения и отправки клиентом {} формы регистрации.",
                    userRole);
            return signup;
        }

        log.info(" Выход клиента {} из авторизации, "
                + "перенаправление на дом. страницу.", userRole);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/signup.do")
    public ResponseEntity signupStatus(
            @ModelAttribute("userData") SignInData signInData) {

        return accountServiceImpl.addUser(signInData)
                ? new ResponseEntity(HttpStatus.CREATED)
                : new ResponseEntity(HttpStatus.CONFLICT);
    }
}