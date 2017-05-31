package com.alb.test.unit.web;

import com.alb.web.VisitCounter;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Проверяет корректность работы spring-компонентов приложения.
 * 
 * @author Альберт
 */
//@RunWith(MockitoJUnitRunner.class)
public class ComponentTest {
    @InjectMocks
    private VisitCounter visitCounter = new VisitCounter();
    
    /**
     * Проверяет корректность работы компонента {@code VisitCounter}, 
     * ответственного за учет статистики посещения приложения.
     */
    @Test
    public void testVisitCounterComponent() {
        visitCounter.increase();
      
        assertEquals(1, visitCounter.getCurrentCount());        
        
        visitCounter.increase();
        visitCounter.decrease();
        visitCounter.decrease();
        
        assertEquals(0, visitCounter.getCurrentCount()); 
        assertEquals(2, visitCounter.getTotalCount()); 
    } 
}