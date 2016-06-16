package by.it.academy.adorop.web.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestParamValidatorTest {

    private static final String ANY_STRING = "any string";

    @Test
    public void whenParameterIsNull() throws Exception {
        isEmptyShouldReturnTrueWhenParameterIs(null);
    }

    @Test
    public void whenParameterIsEmptyString() throws Exception {
        isEmptyShouldReturnTrueWhenParameterIs("");
    }

    @Test
    public void areEmptyShouldReturnTrueWhenAtLeastOneOfParametersIsNull() throws Exception {
        assertTrue(RequestParamValidator.areEmpty(ANY_STRING, ANY_STRING, null));
    }

    @Test
    public void areEmptyShouldReturnFalseWhenAllParametersAreNotEmpty() throws Exception {
        assertFalse(RequestParamValidator.areEmpty(ANY_STRING, ANY_STRING, ANY_STRING));
    }

    @Test
    public void testIsPositiveIntegerWhenParameterIsNullOrNotNumericStringOrLessThenZero() throws Exception {
        isPositiveIntegerShouldReturnFalse(null);
        isPositiveIntegerShouldReturnFalse(ANY_STRING);
        isPositiveIntegerShouldReturnFalse("-1");
    }

    @Test
    public void isPositiveIntegerShouldReturnTrueWhenStringIsNumeric() throws Exception {
        assertTrue(RequestParamValidator.isPositiveInt("1"));
    }

    @Test
    public void testIsNumberBetweenZeroAndTen() throws Exception {
        isNumberBetweenZeroAndTenShouldReturnFalseWhenParameterIs("12");
        isNumberBetweenZeroAndTenShouldReturnFalseWhenParameterIs(ANY_STRING);
        isNumberBetweenZeroAndTenShouldReturnFalseWhenParameterIs("-1");
    }

    private void isNumberBetweenZeroAndTenShouldReturnFalseWhenParameterIs(String parameter) {
        assertFalse(RequestParamValidator.isNumberBetweenZeroAndTen(parameter));
    }

    private void isPositiveIntegerShouldReturnFalse(String parameter) {
        assertFalse(RequestParamValidator.isPositiveInt(parameter));
    }

    private void isEmptyShouldReturnTrueWhenParameterIs(String parameter) {
        assertTrue(RequestParamValidator.isEmpty(parameter));
    }
}