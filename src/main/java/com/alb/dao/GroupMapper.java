package com.alb.dao;

import com.alb.model.po.ContentGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Описывает методы поиска наименований групп единиц контента в бд.
 * 
 * @author Альберт
 */
@Mapper
public interface GroupMapper {
    
    /**
     * Реализующий метод должен вернуть список коллекций контента,
     * упорядоченный по имени.
     * 
     * @return  список коллекций контента
     */
    @Select("select * from content_group order by name")
    List<ContentGroup> findAll();
    
    /**
     * Реализующий метод должен вернуть запись коллекции контента по ее
     * номеру.
     * 
     * @param id    номер коллекции
     * @return  объект с записью коллекции контента
     */
    @Select("select * from content_group where id = #{id}")
    ContentGroup findById(@Param("id") long id);
    
    /**
     * Реализующий метод должен вернуть запись коллекции контента по ее
     * наименованию.
     * 
     * @param name  наименование коллекции  
     * @return  объект с записью коллекции контента
     */
    @Select("select * from content_group where name = #{name}")
    ContentGroup findByName(@Param("name") String name);
}