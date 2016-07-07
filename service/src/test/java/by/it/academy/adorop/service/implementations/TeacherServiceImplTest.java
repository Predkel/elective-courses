package by.it.academy.adorop.service.implementations;

import by.it.academy.adorop.dao.api.MarkDAO;
import by.it.academy.adorop.dao.api.UserDAO;
import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TeacherServiceImplTest {
    public static final long ANY_LONG = 1L;
    private TeacherService teacherService;
    @Mock
    private UserDAO<Teacher> userDAO;
    @Mock
    private MarkDAO markDAO;
    private Mark mark;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        teacherService = new TeacherServiceImpl(userDAO, markDAO);
        mark = new Mark();
    }

    @Test(expected = IllegalArgumentException.class)
    public void evaluateShouldThrowIllegalArgumentExceptionWhenMarkIdIsNull() throws Exception {
        teacherService.evaluate(mark);
    }

    @Test
    public void evaluateShouldRetrieveMarkWhenCourseOrStudentAreNull() throws Exception {
        mark.setId(ANY_LONG);
        teacherService.evaluate(mark);
        verify(markDAO).get(ANY_LONG);
    }
}