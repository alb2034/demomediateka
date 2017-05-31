package com.alb.service;

import com.alb.model.po.Content;
import com.alb.model.vo.Description;
import com.alb.model.vo.ContentPresentator;
import java.util.List;

/**
 * Описывает методы, предоставляющие сервисы для работы с записями контента.
 * 
 * @author Альберт
 */
public interface ContentService {
    /**
     * Описывает метод, предоставляющий сервисы для добавления в бд 
     * нового контента.
     * 
     * @param   content
     *          данные о контенте для добавления записи
     * @return  {@code true} в случае успеха работы сервиса,
     *          {@code false} в противном случае
     */
    boolean add(Content content);
    /**
     * Описывает метод, предоставляющий сервисы для удаления контента из бд.
     * 
     * @param   id
     *          уникальный номер удаляемого контента
     * @return  {@code true} в случае успеха работы сервиса,
     *          {@code false} в противном случае
     */
    boolean delete(long id);    
    /**
     * Описывает метод, предоставляющий сервисы для поиска контента в бд.
     * 
     * @param   id
     *          уникальный номер искомого контента
     * 
     * @return  {@code true} в случае успеха работы сервиса,
     *          {@code false} в противном случае
     */
    Content getById(long id);
    
    /**
     * Описывает метод, предоставляющий сервисы для подгрузки описания к контенту.
     * 
     * @param   id
     *          уникальный номер контента
     * 
     * @return  объект, представляющий описание к контенту или {@code null},
     * если не найдено.
     */
    Description getDescriptionById(long id);
    /**
     * Описывает метод, предоставляющий сервис для редактирования контента в бд.
     * 
     * @param   content
     *          данные о контенте, в соответствии с которым производится поиск и
     *          редактирование контента.
     * @return  объект с описанием контента в случае успеха работы сервиса,
     *          {@code null} в противном случае
     */
    boolean edit(Content content);
    
    /**
     * Описывает метод, предоставляющий сервис для получения списка единиц
     * контента из бд.
     * 
     * @return список единиц контента
     */
    List<Content> getAll();
    /**
     * Описывает метод, предоставляющий сервис для получения списка единиц
     * контента без детального описания, с соотвествующим наименованием группы
     * и с учетом пейджинга (выборки по частям).
     * 
     * @param limit     число записей для выборки
     * 
     * @param offset    кол-во невключающихся в результат записей с начала
     *                  выборки. Н-р,
     *                  {@code limit=10, offset=10}, всего найдено 100, но
     *                  из них будут отобраны только записи с 11 по 20
     *                  
     * @return  список единиц контента
     */
    List<ContentPresentator>
        getAllWithGroupName(long limit, long offset);
    /**
     * Описывает тот же метод, что и 
     * {@link #getAllWithGroupName(long, long)},но с учетом фильтра.
     * 
     * @param contentPattern
     * @param limit     
     * @param offset    
     * @return 
     */    
    List<ContentPresentator>
    getAllWithFilterSearch(Content contentPattern, long limit, long offset);
    /**
     * Описывает метод, предоставляющий сервис, возвращающий кол-во всех 
     * единиц контента в библиотеке.
     * 
     * @return число единиц контента
     */
    long getSize();
    /**
     * Описывает метод, предоставляющий сервис, возвращающий кол-во единиц
     * контента после применения фильтра.
     * 
     * @param   contentPattern
     *          объект с данными для фильтра
     * 
     * @return число единиц контента
     */
    long getSizeAfterFilterSearch(Content contentPattern);
}