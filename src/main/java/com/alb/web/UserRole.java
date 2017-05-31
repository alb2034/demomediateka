package com.alb.web;

import com.alb.model.po.Account;
import com.alb.model.vo.SignInData;
import com.alb.model.vo.Role;
import com.alb.util.web.UserRoleType;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Определяет по логину и хэшированному паролю, а также хранит роль пользователя 
 * при создании новой сессии. При завершении сессии, учитывается статистика 
 * посещения пользователей в классе {@link VisitCounter}.  Сериализуется на
 * случай остановки, перезапуска сервера.
 * @author Альберт
 */
@Component
@SessionScope
@Slf4j
public class UserRole extends Role {
     
    @Autowired
    private transient VisitCounter visitCounter;
    
    /**
     * Создает новый объект {@code UserRole} и инициализирует его 
     * гостевой ролью пользователя.
     */
    public UserRole() {
        role = UserRoleType.GUEST;

    }
      
   /**
     * Сравнивает данные формы аутентификации {@code signInData} с
     * данными из БД, приведенными в аргументе {@code account}.
     * 
     * @param   signInData
     *          объект с логином и паролем, введенных в форме аутентификации
     *                    
     * @param   account
     *          объект из БД с паролем и таким же логином как в объекте 
     *          из первого аргумента
     */
    
    public void setRole(SignInData signInData,
            Account account) {

        role = UserRoleType.GUEST;

        if (account == null)
            return;

        String hashAuthPassword =
                DigestUtils.sha256Hex(signInData.getPassword());

        if (account.getPassword().equals(hashAuthPassword)) {
            if (account.isAdmin())
                role = UserRoleType.ADMIN;
            else
                role = UserRoleType.USER;
        }
    }
     
    /**
     * Возвращает тип роли пользователя, представленного данным объектом.
     * @return 
     */
    public UserRoleType getRole() {
        return role;
    }
       
    @PreDestroy
    private void decreaseUserCount() {
        visitCounter.decrease();
        log.info("Клиент {} вышел из авторизации, "
                + "сейчас на сайте: {}, всего посещений: {}", 
                new Object[]{ 
                    this, visitCounter.getCurrentCount(), 
                    visitCounter.getTotalCount()
                });
    }
    private static final long serialVersionUID = 8061304280195162397L;
}        