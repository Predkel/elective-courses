package by.it.academy.adorop.service.implementations.with.database.visit;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.CourseService;
import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.PersistenceTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, PersistenceTestConfig.class})
public class CourseServiceImplTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    @Qualifier("courseServiceImpl")
    private CourseService courseService;

    @Test
    public void testGetCount() throws Exception {
        assertSame(3L, courseService.getCount());
    }

    @Test
    public void isAlreadyExistShouldReturnFalse() throws Exception {
        Course notExistingCourse = buildCourse("title_55");
        assertFalse(courseService.isAlreadyExists(notExistingCourse));
    }

    @Test
    public void isAlreadyExistsShouldReturnTrue() throws Exception {
        Course existingCourse = buildCourse("title_0");
        assertTrue(courseService.isAlreadyExists(existingCourse));
    }

//    @Test(expected = ServiceException.class)
//    public void shouldThrowServiceExceptionWhenAttemptToPersistCourseWithoutTeacherOccurred() throws Exception {
//        Course courseWithoutTeacher = new Course();
//        courseWithoutTeacher.setTitle("some Title");
//        courseService.persist(courseWithoutTeacher);
//    }

    private Course buildCourse(String title) {
        Course course = new Course();
        course.setTitle(title);
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        course.setTeacher(teacher);
        return course;
    }
}