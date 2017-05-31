package com.alb.web;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

/**
 * Учитывает статистику посещения пользователей.
 * Для подсчета использует потобезопасные поля, т.к экземпляр
 * данного класса общий для всех клиентов.
 * @author Альберт
 */
@Component
public class VisitCounter {
    // текущее количество посетителей
    private final AtomicLong CURRENT_COUNT = new AtomicLong(0);
    // общее количество посетителей    
    private final AtomicLong TOTAL_COUNT = new AtomicLong(0);

    /** Увеличивает текущее и общее количество посетителей на одно значение. */
    public void increase() {
        CURRENT_COUNT.incrementAndGet();
        TOTAL_COUNT.incrementAndGet();  
    }
    
    /** Уменьшает текущее количество посетителей на одно значение. */
    public void decrease() {
        CURRENT_COUNT.decrementAndGet();
    }
    
    /** Возвращает текущее количество посетителей.
     * @return  */
    public long getCurrentCount(){
        return CURRENT_COUNT.get();
    }
    
    /** Возвращает общее количество посетителей.
     * @return  */
    public long getTotalCount(){
        return TOTAL_COUNT.get();
    }
}