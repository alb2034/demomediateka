package com.alb.test.functional.pom;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;

/**
 * Класс для тестирования страницы авторизации.
 * 
 * @author Альберт
 */
public class LoginPage {
    private final SelenideElement login;
    private final SelenideElement password;
    private final SelenideElement authorize;

    /**
     * Инициализирует элементы страницы.
     */
    public LoginPage() {
        this.login = $(byName("login"));
        this.password = $(byName("password"));
        this.authorize = $(byXpath("//span[@widgetid='auth_btn']"));
    }
    
    /**
     * Заполняет текстовое поле с логином
     * 
     * @param   login
     *          логин для текстового поля
     * 
     * @return  ссылка на данный объект 
     */
    public LoginPage setLogin(String login) {
        this.login.setValue(login);
        return this;
    }
    
    /**
     * Заполняет текстовое поле с паролем
     * 
     * 
     * @param   password
     *          пароль для текстового поля
     * 
     * @return  ссылка на данный объект 
     */
    public LoginPage setPassword(String password) {
        this.password.setValue(password);
        return this;
    }
    
    /**
     * Имитирует нажатие пользователя кнопки Войти
     * 
     * @return  ссылка на данный объект 
     */
    public LoginPage clickAuthorize() {
        authorize.click();
        return this;
    }
    
    /**
     * Проверяет, открыта ли страница, представленная данным объектом
     * 
     * @return  ссылка на данный объект 
     */
    public LoginPage shouldBeOpened() {
        authorize.shouldBe(Condition.visible);
        return this;
    }
}