package by.it.academy.adorop.service.implementations.with.database.visit;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MarkServiceImplTest extends AbstractIntegrationTest {
    @Autowired
    private MarkService markService;
    @Test
    public void testNestedRestrictions() throws Exception {
        Map<String, Object> restrictions = new HashMap<>();
        restrictions.put("course.teacher.documentId", "adorop");
        assertSame(3, markService.findBy(restrictions).size());
    }

    @Test
    public void testMultipleNestedRestrictions() throws Exception {
        Map<String, Object> restrictions = new HashMap<>();
        restrictions.put("course.teacher.documentId", "adorop");
        restrictions.put("student.firstName", "1stName");
        assertSame(2, markService.findBy(restrictions).size());
    }
}