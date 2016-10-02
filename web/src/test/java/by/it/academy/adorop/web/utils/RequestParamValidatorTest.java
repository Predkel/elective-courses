package by.it.academy.adorop.web.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestParamValidatorTest {

    @Test
    public void isNumericShouldReturnFalseWhenParameterIsNotNumeric() throws Exception {
        assertFalse(RequestParamValidator.isNumeric("abc"));
    }

    @Test
    public void isNumericShouldReturnTrueWhenParameterIsNegativeNumber() throws Exception {
        assertTrue(RequestParamValidator.isNumeric("-1"));
    }
}