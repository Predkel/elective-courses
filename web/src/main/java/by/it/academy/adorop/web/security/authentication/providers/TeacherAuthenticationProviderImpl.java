package by.it.academy.adorop.web.security.authentication.providers;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.TeacherService;
import by.it.academy.adorop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherAuthenticationProviderImpl extends AbstractAuthenticationProvider<Teacher> implements TeacherAuthenticationProvider {

    private final TeacherService teacherService;

    @Autowired
    public TeacherAuthenticationProviderImpl(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    protected UserService<Teacher> getUserService() {
        return teacherService;
    }
}
