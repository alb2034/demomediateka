package com.alb.controller.rest;

import com.alb.model.po.Content;
import com.alb.model.vo.Description;
import com.alb.util.controller.rest.RangeHeader;
import com.alb.service.ContentService;
import com.alb.web.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Rest-сервис, реализующий CRUID-операции над единицами контента. Кроме того,
 * сервис имеет функцую подгрузки детального описания контента по его id,
 * т.к в таблицу с единицами контента не включается описание с целью оптимизации
 * затратов траффика.
 * 
 * @author Альберт
 */
@RestController
@Slf4j
@SuppressWarnings("unchecked")
public class ManyContentController {
    
    @Autowired
    private ContentService contentService;
    
    @Autowired
    private UserRole userRole;

    /** Добавляет новую запись о контенте.
     * 
     * @param content  информация для новой записи
     * 
     * @return  {@code HttpStatus.CREATED} статус и 
     *          {@code Location: /manyContent/{id}} заголовок с в случае успеха, где
     *          {@code id} - номер новой записи.
     *          {@code HttpStatus.BAD_REQUEST} в противном случае.
     */
    @PostMapping("/manyContent")
    public ResponseEntity createContent(@RequestBody Content content) {

        if (!contentService.add(content))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        HttpHeaders header = new HttpHeaders();
        String location = "/manyContent/" + content.getId();
        header.add("Location", location);
        
        log.info("Пользователь с ролью {} добавил новую запись с контентом, "
                + "доступный по адресу {}", userRole.getRole(), location);
        
        return new ResponseEntity(header, HttpStatus.CREATED);
    }
    
    /** 
     * Возвращает список единиц контента с учетом пейджинга (по частям) либо пустой,
     * если запрос ничего не вернул.
     * 
     * @param range заголовок @{code Range} c диапозоном записей, по которому 
     *              определяется какую часть результата возвращать
     * @return  список единиц контента и {@code HttpStatus.OK}
     */
    @GetMapping("/manyContent")
    public ResponseEntity getManyContent(
        @RequestHeader(value = "Range", required = false) String range) {
        
        log.info("Клиент {} отправил запрос на список с заголовком Range: {}",
                userRole, range);
        
        RangeHeader rangeHeader = new RangeHeader(range);
        rangeHeader.setTotalLimit(contentService.getSize());
        
        if (!rangeHeader.isCorrect())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        rangeHeader.fixLargeLimit(15);
        
        long limit = rangeHeader.getLimit();
        long offset = rangeHeader.getOffset();       
        long total = rangeHeader.getTotalLimit();
        
        if (total < limit)
            rangeHeader.fixLargeLimit((int) total);
        
        HttpHeaders headers = new HttpHeaders();
        String contentRange = "items " +
            rangeHeader.getFromRange() + "-" +
            rangeHeader.getToRange() + "/" +
            total;
        headers.add("Content-Range", contentRange);
        
        log.info("Клиент {} получил заголовок Content-Range: {}",
                userRole, contentRange);
        
        return new ResponseEntity(
            contentService.
                    getAllWithGroupName(limit, offset),
            headers, HttpStatus.OK);
        
    }
    
    /**
     * Возвращает список единиц контента с учетом фильтра и пейджинга, т.е. по частям.
     * 
     * @param name  параметр запроса - имя контента
     * @param author    параметр запроса - автор контента
     * @param groupId параметр запроса - группа единиц контента
     * @param range параметр запроса - диапозон записей, по которому 
     * определяется какую часть запроса возвращать.
     * 
     * @return  список отфильтрованных  единиц контента с учетом пейджинга,
     *          строка с информацией 
     */
    @GetMapping("/manyContent/fsearch")
    public ResponseEntity getManyContentWithFilterSearch(
        @RequestParam("name") String name, 
        @RequestParam("author") String author, 
        @RequestParam("groupId") Long groupId,
        @RequestHeader(value = "Range", required = false) String range) {
           
        log.info("Клиент {} отправил запрос на фильтрованный список "
                + "с заголовком Range: {}", userRole, range);
        
        Content contentPattern = new Content(addPercents(name), addPercents(author));
        contentPattern.setGroupId(groupId);
        
        RangeHeader rangeHeader = new RangeHeader(range);
        rangeHeader.setTotalLimit(contentService.getSizeAfterFilterSearch(contentPattern));

        if (!rangeHeader.isCorrect())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        rangeHeader.fixLargeLimit(15);
        
        long limit = rangeHeader.getLimit();
        long offset = rangeHeader.getOffset();       
        long total = contentService.getSizeAfterFilterSearch(contentPattern);
        
        if (total < limit)
            rangeHeader.fixLargeLimit((int) total);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range", "items " +
            rangeHeader.getFromRange() + "-" +
            rangeHeader.getToRange() + "/" +
            total);

        return new ResponseEntity(contentService
            .getAllWithFilterSearch(contentPattern, limit, offset),
            headers, HttpStatus.OK);              
    }
    
    /**
     * Редактирует запись контента.
     * 
     * @param id    номер редактируемой записи
     * @param content  объект, представляющий редактируемый контент
     *
     * @return  {@code HttpStatus.NO_CONTENT} статус в случае успеха
     *          {@code HttpStatus.NOT_FOUND} если не найдена искомая запись. 
     */
    @PutMapping("/manyContent/{id}")
    public ResponseEntity editContent(
            @PathVariable long id,
            @RequestBody Content content) {
        if (!contentService.edit(content)) {
            log.info(
                    "Запись контента с уникальным номером {} для " + 
                    "редактирования пользователя с ролью {} не найдена",
                    id, userRole.getRole());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
            else {
            log.info(
                    "Пользователь с ролью {} успешно изменил запись " +
                    "контента с уникальным номером {}", userRole.getRole(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
    
    /**
     * Удаляет запись контента.
     * 
     * @param id    номер удаляемой записи
     *
     * @return  {@code HttpStatus.NO_CONTENT} статус в случае успеха
     *          {@code HttpStatus.NOT_FOUND} если не найдена искомая запись. 
     */
    @DeleteMapping("/manyContent/{id}")
    public ResponseEntity deleteContent(@PathVariable long id) {
        if (!contentService.delete(id)) {
            log.info(
                    "Запись контента с уникальным номером {} для " + 
                    "удаления пользователем с ролью {} не найдена",
                    id, userRole.getRole());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else {
            log.info(
                    "Пользователь с ролью {} успешно удалил запись " +
                    "контента уникальным номером {}", 
                    userRole.getRole(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);     
        }
    }
    
    /**
     * Подгружает детальное описание контента.
     * 
     * @param id    номер записи контента, описание которого требуется.
     *
     * @return  {@code HttpStatus.OK} статус в случае успеха
     *          {@code HttpStatus.NOT_FOUND} если не найдена искомая запись. 
     */
    @GetMapping("/description/{id}")
    public ResponseEntity getDescription(@PathVariable("id") long id) {

        Description description = 
                contentService.getDescriptionById(id);
        
        return description != null 
                ? new ResponseEntity(description, HttpStatus.OK)
                : new ResponseEntity(HttpStatus.NOT_FOUND);     
    }
    
    private String addPercents(String s) {
        return "%" + s + "%";
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> error(Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }
}