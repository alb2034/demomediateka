package com.alb.service.impl;

import com.alb.model.po.Content;
import com.alb.model.vo.Description;
import com.alb.model.vo.ContentPresentator;
import com.alb.dao.ContentMapper;
import com.alb.service.ContentService;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Реализует методы, описанные в интерфейсе {@link ContentService}.
 * 
 * @author Альберт
 */
@Service
@Slf4j
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;
    
    @Override
    public boolean add(Content content) {
        try {
            contentMapper.insert(content);
            return true;
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean delete(long id) {
        //false - если запись отсутствует
        return (contentMapper.delete(id) > 0);
    }

    @Override
    public boolean edit(Content contentWithNewData) {
        Content content =
                contentMapper.findById(contentWithNewData.getId());
        if (content == null)
            return false;
        
        try {
            contentMapper.edit(contentWithNewData);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<Content> getAll() {
        return contentMapper.findAll();
    }
    
    @Override
    public List<ContentPresentator> getAllWithGroupName(long limit, long offset) {
        return contentMapper.findAllWithGroupName(limit, offset);
    }
    
    @Override
    public List<ContentPresentator> getAllWithFilterSearch(Content contentPattern, long limit, long offset) {
        return contentMapper.findAllWithFilter(contentPattern, limit, offset);
    }

    @Override
    public Content getById(long id) {
        return contentMapper.findById(id);
    }

    @Override
    public Description getDescriptionById(long id) {
        Content content = getById(id);
        return content == null
                ? null
                : new Description(content.getDescription());
    }

    @Override
    public long getSize() {
        return contentMapper.selectCount();
    }
    
    @Override
    public long getSizeAfterFilterSearch(Content contentPattern) {
        return contentMapper.selectCountAfterFilter(contentPattern);
    }
}