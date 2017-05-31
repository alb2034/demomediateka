package com.alb.service;

import com.alb.model.po.Account;
import com.alb.model.vo.SignInData;
import java.util.List;

public interface AccountService {
    /**
     * Добавляет в БД новую учетную запись для регистрации пользователя.
     *
     * @param   signUpData
     *          объект с логином и паролем для регистрации пользователя
     *
     * @return  {@code true} в случае успеха,
     *          {@code false} в противном случае
     */
    boolean addUser(SignInData signUpData);
    /**
     * Ищет в БД пользователя с заданным логином в аргументе и
     * возвращает его реквезиты.
     *
     * @param   signInData
     *          объект с логином пользователя
     *
     * @return  {@code Account} или null если пользователя с заданным логином в
     *          БД не обнаружено
     */
    Account getUser(SignInData signInData);

    List<Account> getUserList();
}