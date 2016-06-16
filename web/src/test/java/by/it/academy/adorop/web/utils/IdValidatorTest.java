package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestParamValidator.class})
public class IdValidatorTest {

    private static final String ANY_STRING = "1";
    private IdValidator idValidator;

    @Mock
    private Service service;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RequestParamValidator.class);
        idValidator = new IdValidator(service);
    }

    @Test
    public void whenParameterIdNotPositiveNumber() throws Exception {
        PowerMockito.when(RequestParamValidator.isPositiveInt(anyString())).thenReturn(false);
        isValidShouldReturnFalse();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenEntityWithThatIdDoesNotExist() throws ServiceException {
        PowerMockito.when(RequestParamValidator.isPositiveInt(anyString())).thenReturn(true);
        when(service.find(anyLong())).thenReturn(null);
        isValidShouldReturnFalse();
    }

    private void isValidShouldReturnFalse() throws by.it.academy.adorop.service.exceptions.ServiceException {
        assertFalse(idValidator.isValid(ANY_STRING));
    }
}