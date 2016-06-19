package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class RequestParamValidatorTest {
    private static final String NOT_NUMERIC_STRING = "abc";
    public static final String NUMERIC_STRING = "1";
    @Mock
    Service service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isValidIdShouldReturnFalseWhenIdParameterIsNotNumeric() throws Exception {
        assertFalse(RequestParamValidator.isValidId(NOT_NUMERIC_STRING, service));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void isValidIdShouldReturnTrueWhenInstanceWithGivenIdExists() throws Exception {
        Teacher teacher = new Teacher();
        when(service.find(anyLong())).thenReturn(teacher);
        assertTrue(RequestParamValidator.isValidId(NUMERIC_STRING, service));
    }
}