package com.alb.service.impl;

import com.alb.service.AccountService;
import com.alb.dao.AccountRepository;
import com.alb.model.vo.SignInData;
import org.springframework.beans.factory.annotation.Autowired;

import com.alb.model.po.Account;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * Предназначен для удобного поиска данных учетной записи пользователя в БД
 * по заданному логину
 * 
 * @author Альберт
 */
@Service

public class AccountServiceImpl implements AccountService {
    
    /**
     * MyBatis интерфейс для выполнения запроса к БД
     */
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    private AccountMapper accountMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean addUser(SignInData signUpData) {

        Account newAccount = getUser(signUpData);
        if (newAccount != null)
            return false;
        
        Account newAcc;
        newAcc = new Account();
        
        newAcc.setLogin(signUpData.getLogin());
       
        newAcc.setPassword( DigestUtils.sha256Hex(signUpData.getPassword()));

        return (accountRepository.addNewAccount(newAcc) != null);
    }

    @Override
    public List<Account> getUserList() {
        return accountRepository.getUserList();
    }

    @Override
    public Account getUser(SignInData signInData) {
        return accountRepository.findByLogin(signInData.getLogin());
    }   
}