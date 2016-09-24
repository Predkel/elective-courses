package by.it.academy.adorop.service.implementations.with.database.visit;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.config.ServiceConfig;
import by.it.academy.adorop.service.exceptions.ServiceException;
import by.it.academy.adorop.service.implementations.PersistenceTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, PersistenceTestConfig.class})
public class TeacherServiceImplTest {
    @Autowired
    private TeacherService teacherService;

    @Test
    public void isAlreadyExistsShouldReturnTrueWhenAnyUserWithGivenDocumentIdAlreadyExistsInDatabase() throws Exception {
        Teacher teacherWithDocumentIdWhichAlreadyPresentedByStudent = new Teacher();
        teacherWithDocumentIdWhichAlreadyPresentedByStudent.setDocumentId("adorop88");
        assertTrue(teacherService.isAlreadyExists(teacherWithDocumentIdWhichAlreadyPresentedByStudent));
    }

    @Test
    public void isAlreadyExistsShouldReturnFalseWhenGivenDocumentIdIsNew() throws Exception {
        Teacher teacherWithUniqueDocumentId = new Teacher();
        teacherWithUniqueDocumentId.setDocumentId("uniqueDocumentId");
        assertFalse(teacherService.isAlreadyExists(teacherWithUniqueDocumentId));
    }

    @Test(expected = ServiceException.class)
    public void persistShouldThrowServiceExceptionWhenAttemptToInsertNonUniqueDocumentIdHasOccurred() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setDocumentId("adorop88");
        teacher.setPassword("password");
        teacher.setFirstName("anyName");
        teacher.setLastName("anyName");
        teacherService.save(teacher);
    }

    @Test
    public void getSingleResultShouldReturnTeacherFromDatabase() throws Exception {
        Teacher existingTeacher = teacherService.findSingleResultBy("documentId", "adorop");
        assertEquals("adorop", existingTeacher.getDocumentId());
        assertEquals("1234", existingTeacher.getPassword());
        assertEquals("first Name", existingTeacher.getFirstName());
        assertEquals("Last Name", existingTeacher.getLastName());
    }
}