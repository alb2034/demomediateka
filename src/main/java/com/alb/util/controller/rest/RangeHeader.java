package com.alb.util.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Служит для легкой работы с заголовком запроса Range.
 * 
 * @author Альберт
 */
@Slf4j
public class RangeHeader {
    private long limit;
    private long totalLimit;

    
    private long offset;
    private boolean correct;
    private String fromRange;
    private String toRange;
    
    /**
     * Возвращает левую границу диапозона выборки.
     * 
     * @return  строковое представление числа правой границы 
     */
    public String getFromRange() {
        return fromRange;
    }
    
    /**
     * Возвращает правую границу диапозона выборки.
     * 
     * @return  строковое представление числа левой границы 
     */
    public String getToRange() {
        return toRange;
    }
    
    /**
     * Определяет, правильно ли составлен заголовок, представленный 
     * данным объектом.
     * 
     * @return  {@code true}, если правильно,
     *          {@code true} в противном случае
     */
    public boolean isCorrect() {
        return correct;
    }
    
    /**
     * Возвращает максимальное кол-во записей в каждой порции, 
     * требуемое для запроса
     * 
     * @return  максимальное число записей 
     */
    public long getLimit() {
        return limit;
    }
    
    /**
     * Устанавливает в заголовке максимальное кол-во записей в каждой порции, 
     * требуемое для запроса
     * 
     * @param limit  максимальное число записей в каждой порции выборки
     */
    private void setLimit(long limit) {
        this.limit = limit;
        toRange = String.valueOf(offset + limit - 1);
    }
  
    /**
     * Возвращает кол-во игнорируемых записей начиная с первого.
     * 
     * @return  число игнорируемых записей начиная с первого
     */
    public long getOffset() {
        return offset;
    }
    
    /**
     * Возвращает максимальное общее кол-во записей, требуемое для запроса
     * 
     * @return максимальное общее число записей в выборке
     */
    public long getTotalLimit() {
        return totalLimit;
    }

    /**
     * Устанавливает в заголовке максимальное общее кол-во записей, 
     * требуемое для запроса
     * @param totalLimit
     */
    public void setTotalLimit(long totalLimit) {       
        if (totalLimit > 0 && totalLimit < 500)
            this.totalLimit = totalLimit;
    }
    
    /**
     * Проверяет, чтобы в заголовке требовалось кол-во возвращаемых записей,
     * не превышающее значение, указанное в аргументе.
     * 
     * @param   maxLimit 
     *          максимальное значение лимита записей
     */
    public void fixLargeLimit(int maxLimit) {
        if (limit > maxLimit)
            setLimit(maxLimit);
    }
    
    /**
     * Иницирует объект, извлекая из Http-заголовку "Range" числовые параметры
     * запроса: лимит порции результата, левая и правая границы запроса. 
     * Определяет, правильно ли составлен запрос в заголовке. Устанавливает
     * общий лимит на выборку записей, равный 500.
     * 
     * @param   rangeHeader 
     *          http-заголовок "Range" в строковом представлении 
     */
    public RangeHeader(String rangeHeader) {
        String[] rangeValues = 
                rangeHeader.replaceFirst("^[^0-9]*", "").split("-");       
       
        fromRange = rangeValues[0]; 
        toRange = rangeValues[1];
        
        try {
            offset = Long.parseLong(fromRange);
            limit = Long.parseLong(toRange) - offset + 1;
            correct = true;
        } catch (NumberFormatException nfe) {
            log.error(nfe.getMessage());
            correct = false;
        }  
        
        totalLimit = 500;
    }
}