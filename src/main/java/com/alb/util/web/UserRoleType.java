package com.alb.util.web;

/**
 * Перечисление типов ролей пользователей
 * <ul><li>{@link #GUEST}</li>
 * <li>{@link #USER}</li>
 * <li>{@link #ADMIN}</li></ul>
 * @author Альберт
 */

public enum UserRoleType {
    /** Гость */
    GUEST("гость"),
    /** Пользователь */
    USER("пользователь"),
    /** Администратор */
    ADMIN("администратор");
    
    @SuppressWarnings("FieldMayBeFinal")
    private String description;

    UserRoleType(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return description;
    }  
}