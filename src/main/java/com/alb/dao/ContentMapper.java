package com.alb.dao;

import com.alb.dao.sql.ContentFilterSqlBuilder;
import com.alb.model.po.Content;
import com.alb.model.vo.ContentPresentator;
import java.util.List;
import org.apache.ibatis.annotations.*;

/**
 * Описывает методы для редактирования записей единиц контента в бд.
 * 
 * @author Альберт
 */
@Mapper
public interface ContentMapper {
    
    /**
     * Строка запроса добавления новой записи контента.
     */
    String INSERT = "insert into content "
            + "(name, author, description, groupId) "
            + "values (#{name}, #{author}, #{description}, #{groupId})";
    
    /**
     * Строка запроса выборки всех единиц контента.
     */
    String SELECT_ALL = "select * from content ";

    /**
     * Строка запроса подсчета всех единиц контента.
     */
    String SELECT_ALL_COUNT = "select count(*) from content ";
    
    /**
     * Строка запроса выборки записей единиц контента с наименованием группы и без
     * детального описания.
     */
    String SELECT_ALL_WITH_GROUP_NAME =
            "select content.id, content.name, "
            + "author, content_group.name as \"group\" "
            + "from content, content_group "
            + "where groupId=content_group.id ";
    
    /**
     * Часть строки запроса, добавляющая сортировку по группам и имени
     * контента и лимит для выборки.
     */
    String ORDER_AND_LIMIT = 
            "order by groupId, content.name "
            + "offset #{offset} rows fetch next #{limit} rows only";
    
    /**
     * Описывает метод добавления новой записи. Реализующий метод также 
     * сохраняет номер новой записи.
     * 
     * @param content  используется для заполнения новой записи, а также для
     *              хранения {@code id} новой записи; 
     * 
     * @return  {@code 1} если успешно,
     *          {@code 0} в противном случае.
     */
    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(Content content);
    
    /**
     * Описывает метод, возвращающий список единиц контента.
     * 
     * @return  список единиц контента
     */
    @Select(SELECT_ALL)
    List<Content> findAll();
    
    /**
     * Описывает метод, возвращающий список единиц контента без детального
     * описания, с соотвествующим наименованием коллекции и с учетом пейджинга 
     * (выборки по частям).
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
    @Select(SELECT_ALL_WITH_GROUP_NAME + ORDER_AND_LIMIT)
    List<ContentPresentator> findAllWithGroupName(
            @Param("limit") long limit, 
            @Param("offset") long offset);
    
    /**
     * Описывает метод, возвращающий запись контента по ее номеру.
     * 
     * @param id    номер записи
     * 
     * @return  объект с записью контента
     */
    @Select(SELECT_ALL + "where id = #{id}")
    Content findById(long id);
    
    /**
     * Описывает метод, возвращающий запись контента по ее наименованию.
     * 
     * @param name    наименование записи
     * 
     * @return  объект с записью контента
     */
    @Select(SELECT_ALL + "where name = #{name}")
    Content findByName(String name);
    
    /**
     * Описывает тот же метод, что и 
     * {@link #findAllWithGroupName(long, long)}, но с учетом фильтра.
     * 
     * @param   content
     *          критерий для поиска контента
     *
     * @param   limit
     *
     * @param   offset
     *
     * @return 
     */
    /*@Select(SELECT_ALL_WITH_GROUP_NAME
        + "and groupId = #{content.groupId} "
        + "and content.name like #{content.name} "
        + "and author like #{content.author} "
        + ORDER_AND_LIMIT)*/
    @SelectProvider(type = ContentFilterSqlBuilder.class, method = "buildFindAllWithFilter")
    List<ContentPresentator> findAllWithFilter(
            @Param("content") Content content,
            @Param("limit") long limit,
            @Param("offset") long offset);
    
    /**
     * Описывает метод, возвращающий кол-во единиц контента
     * 
     * @return число единиц контента
     */
    @Select(SELECT_ALL_COUNT)
    long selectCount();
    
    /**
     * Описывает метод, возвращающий кол-во единиц контента с учетом фильтра
     * 
     * @param content данные о контенте, в соответствии с которым фильтруются записи
     * 
     * @return число единиц контента с учетом фильтра
     */
    @SelectProvider(type = ContentFilterSqlBuilder.class, method = "buildSelectCountAfterFilter")
    long selectCountAfterFilter(@Param("content") Content content);
    
    /**
     * Описывает метод, редактирующий запись контента.
     * 
     * @param content  данные контента, в соответствии с которым
     *              редактируется запись
     * 
     * @return  {@code 1} в случае успеха, 
     *          {@code 0} в противном случае
     */
    @Update("update content set "
            + "name=#{name}, author=#{author}, "
            + "description=#{description}, groupId=#{groupId} "
            + "where id=#{id}")
    int edit(Content content);
    
    /**
     * Описывается метод, удаляющий запись контента.
     * 
     * @param id    номер удаляемого контента
     * 
     * @return  {@code 1} в случае успеха, 
     *          {@code 0} в противном случае
     */
    @Delete("delete from content "
            + "where id = #{id}")
    int delete(long id);       
}