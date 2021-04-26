package com.alb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;


/**
 * Класс для централизованной обработки ошибок
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException e) {
        if (log.isTraceEnabled()) {
            log.trace(e.getMessage(), e);
        } else {
            log.warn(e.getMessage());
        }


        ModelAndView model = new ModelAndView("error/error_404.jsp");
        model.addObject("message", e.getMessage());
        return model;
    }

}
