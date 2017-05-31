package com.alb.controller.rest;

import com.alb.service.GroupService;
import com.alb.model.po.ContentGroup;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest-сервис, предоставляющий список групп единиц контента
 * 
 * @author Альберт
 */
@RestController
public class GroupsController {
    @Autowired
    private GroupService groupService;
        
    /**
     * Возвращает список наименований групп единиц контента
     * 
     * @return  список наименований групп единиц контента
     */
    @GetMapping("/groups")
    public List<ContentGroup> getGroups() {
        return groupService.getAll();
    }
}