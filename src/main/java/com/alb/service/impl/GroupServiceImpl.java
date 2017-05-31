package com.alb.service.impl;

import com.alb.model.po.ContentGroup;
import com.alb.dao.GroupMapper;
import com.alb.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Реализует метод, возвращающий список наименований групп единиц контента из бд.
 * 
 * @author Альберт
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    
    @Override
    public List<ContentGroup> getAll() {
        return groupMapper.findAll();
    }   
}
