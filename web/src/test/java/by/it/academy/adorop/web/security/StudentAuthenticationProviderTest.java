package by.it.academy.adorop.web.security;

import by.it.academy.adorop.model.users.Student;
import by.it.academy.adorop.service.api.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StudentAuthentication.class)
public class StudentAuthenticationProviderTest {
    private static final String ANY_PASSWORD = "any password";
    private StudentAuthenticationProvider authenticationProvider;
    @Mock
    private StudentService studentService;
    @Mock
    private Authentication authentication;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(StudentAuthentication.class);
        authenticationProvider = new StudentAuthenticationProvider(studentService);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void authenticateShouldThrowUserNotFoundExceptionWhenStudentWithGivenDocumentIdDoesNotExist() throws Exception {
        when(studentService.getByDocumentId(anyString())).thenReturn(null);
        authenticationProvider.authenticate(authentication);
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticateShouldThrowBadCredentialsExceptionWhenPasswordsDoNotMatch() throws Exception {
        whenStudentServiceReturnsStudentWith(ANY_PASSWORD);
        String anotherPassword = "another password";
        whenSubmittedPasswordIs(anotherPassword);
        authenticationProvider.authenticate(authentication);
    }

    private void whenSubmittedPasswordIs(String password) {
        when(authentication.getCredentials()).thenReturn(password);
    }

    private void whenStudentServiceReturnsStudentWith(String password) {
        Student student = new Student();
        student.setPassword(password);
        when(studentService.getByDocumentId(anyString())).thenReturn(student);
    }

    @Test
    public void authenticateShould() throws Exception {
        whenAuthenticationAttemptIsSuccessful();
        PowerMockito.when(StudentAuthentication.newInstance(anyObject())).thenReturn(authentication);
        assertSame(authentication, authenticationProvider.authenticate(authentication));
        verify(authentication).setAuthenticated(true);
    }

    private void whenAuthenticationAttemptIsSuccessful() {
        whenStudentServiceReturnsStudentWith(ANY_PASSWORD);
        whenSubmittedPasswordIs(ANY_PASSWORD);
    }

    @Test
    public void testSupports() throws Exception {
        assertTrue(authenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
    }
}