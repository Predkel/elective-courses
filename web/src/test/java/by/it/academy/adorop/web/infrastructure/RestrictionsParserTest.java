package by.it.academy.adorop.web.infrastructure;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.web.infrastructure.filtering.RestrictionsParser;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RestrictionsParserTest {
    private RestrictionsParser restrictionsParser;

    @Before
    public void setUp() throws Exception {
        restrictionsParser = new RestrictionsParser();
    }

    @Test
    public void verifyStringField() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith("title", "title_0", Course.class);
        assertSize(restrictions, 1);
        assertContainsParamName(restrictions, "title");
        assertContainsValue(restrictions, "title_0");
    }

    @Test
    public void testLongField() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith("id", "1", Course.class);
        assertSize(restrictions, 1);
        assertContainsParamName(restrictions, "id");
        assertContainsValue(restrictions, 1L);
    }

    @Test
    public void testIntegerField() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith("value", "1", Mark.class);
        assertSize(restrictions, 1);
        assertContainsParamName(restrictions, "value");
        assertContainsValue(restrictions, 1);
    }

    @Test
    public void testInheritedStringField() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith("firstName", "wayne", Student.class);
        assertSize(restrictions, 1);
        assertContainsParamName(restrictions, "firstName");
        assertContainsValue(restrictions, "wayne");
    }

    @Test
    public void testInheritedLongField() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith("id", "1", Teacher.class);
        assertSize(restrictions, 1);
        assertContainsParamName(restrictions, "id");
        assertContainsValue(restrictions, 1L);
    }

    @Test
    public void testMultipleRestrictions() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith(Student.class,
                new Pair<>("id", "2"),
                new Pair<>("password", "123"),
                new Pair<>("notExistingField", ""));
        assertSize(restrictions, 2);
        assertContainsParamName(restrictions, "id");
        assertContainsParamName(restrictions, "password");
        assertContainsValue(restrictions, 2L);
        assertContainsValue(restrictions, "123");
    }

    @Test
    public void testNestedRestrictions() throws Exception {
        Map<String, Object> restrictions = getRestrictionsWith("teacher.firstName", "wayne", Course.class);
        assertSize(restrictions, 1);
        assertContainsParamName(restrictions, "teacher.firstName");
        assertContainsValue(restrictions, "wayne");
    }

    @Test
    public void testMultipleNestedRestrictions() throws Exception {
        String studentFirstNameParameterName = "student.firstName";
        String studentLastNameParameterName = "student.lastName";
        String courseTeacherIdParameterName = "course.teacher.id";
        Map<String, Object> restrictions = getRestrictionsWith(Mark.class,
                new Pair<>(studentFirstNameParameterName, "wayne"),
                new Pair<>(studentLastNameParameterName, "rooney"),
                new Pair<>(courseTeacherIdParameterName, "1"));
        assertSize(restrictions, 3);
        assertContainsParamName(restrictions, studentFirstNameParameterName);
        assertContainsParamName(restrictions, studentLastNameParameterName);
        assertContainsParamName(restrictions, courseTeacherIdParameterName);
        assertEquals(restrictions.get(studentFirstNameParameterName), "wayne");
        assertEquals(restrictions.get(studentLastNameParameterName), "rooney");
        assertEquals(restrictions.get(courseTeacherIdParameterName), 1L);
    }

    private Map<String, Object> getRestrictionsWith(Class retriedClass, Pair<String, String>... parameters) {
        return restrictionsParser.parse(getMapWithParameters(parameters), retriedClass);
    }

    private Map<String, Object> getRestrictionsWith(String paramName, String paramValue, Class retrievedClass) {
        return restrictionsParser.parse(getMapWithParameters(paramName, paramValue), retrievedClass);
    }

    private void assertContainsValue(Map<String, Object> restrictions, Object expected) {
        assertTrue(restrictions.containsValue(expected));
    }

    private void assertContainsParamName(Map<String, Object> restrictions, String expected) {
        assertTrue(restrictions.containsKey(expected));
    }

    private void assertSize(Map<String, Object> restrictions, int expected) {
        assertSame(expected, restrictions.size());
    }

    private Map<String, String> getMapWithParameters(String paramName, String paramValue) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put(paramName, paramValue);
        return parameters;
    }

    private Map<String, String> getMapWithParameters(Pair<String, String>... parameters) {
        HashMap<String, String> convertedParameters = new HashMap<>();
        for (Pair<String, String> pair : parameters) {
            convertedParameters.put(pair.getKey(), pair.getValue());
        }
        return convertedParameters;
    }


}