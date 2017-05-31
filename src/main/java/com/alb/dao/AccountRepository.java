package com.alb.dao;

import com.alb.model.po.Account;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Альберт on 24.05.2017.
 */
@Repository
@Slf4j
public class AccountRepository {

    @PersistenceContext()
    private EntityManager em;

    @Transactional(transactionManager = "jpaTxManager")
    public Account addNewAccount(Account newAccount) {
        em.persist(newAccount);
        //log.info( "USER LIST IN AccountRepository: {}", getUserList().toString());
        return newAccount;
    }

    public List<Account> getUserList() {
        return em.createQuery("select t from Account t", Account.class).getResultList();
    }

    public Account findByLogin(String login) {

        List<Account> lst = em.createQuery("select t from Account t where t.login=:p1", Account.class)
               .setParameter("p1", login).getResultList();
        if (lst.isEmpty()) 
            return null;
        else 
            return lst.get(0);
    }
}