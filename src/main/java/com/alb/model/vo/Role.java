package com.alb.model.vo;

import com.alb.util.web.UserRoleType;
import java.io.Serializable;

/**
 * Хранит роль пользователя. Используется в представлениях. Сериализуется
 * на случай остановки, перезапуска сервера.
 * @author Альберт
 */
public class Role implements Serializable {

    /**
     * Тип роли пользователя.
     */
    protected UserRoleType role;
    
    /**
     * Инициирует объект гостевой ролью.
     */
    public Role() {
        this.role = UserRoleType.GUEST;
    }
    
    /**
     * Инициирует объект ролью, представленной в аргументе.
     * 
     * @param   role
     *          роль пользователя     
     */
    public Role(UserRoleType role) {
        this.role = role;
    }
    
    /**
     * Проверяет, является ли пользователь, представленный данным 
     * объектом, гостем.
     * 
     * @return  {@code true}, если пользователь - гость,
     *          {@code false} в противном случае.
     */
    public boolean isGuest() {
        return role == UserRoleType.GUEST;
    }
    
    /**
     * Проверяет, является ли пользователь, представленный данным 
     * объектом, зарегестрированным.
     * 
     * @return  {@code true}, если пользователь зарегестрированный,
     *          {@code false} в противном случае.
     */
    public boolean isUser() {
        return role == UserRoleType.USER;
    }
    
    /**
     * Проверяет, является ли пользователь, представленный данным 
     * объектом, администратором.
     * 
     * @return  {@code true}, если пользователь - администратор,
     *          {@code false} в противном случае.
     */
    public boolean isAdmin() {
        return role == UserRoleType.ADMIN;
    }
    
    private static final long serialVersionUID = 6953224438961070514L;
}