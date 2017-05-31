package com.alb.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс для приема-передачи данных для аутентификации.
 */
public class SignInData {

    @Getter @Setter
    private String login;

    @Getter @Setter
    private String password;
}