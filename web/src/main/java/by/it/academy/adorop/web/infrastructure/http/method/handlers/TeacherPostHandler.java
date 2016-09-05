package by.it.academy.adorop.web.infrastructure.http.method.handlers;

import by.it.academy.adorop.model.users.Teacher;
import by.it.academy.adorop.service.api.Service;
import by.it.academy.adorop.service.api.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("teacherPostHandler")
public class TeacherPostHandler extends AbstractPostHandler<Teacher, Long> {
    private final TeacherService teacherService;

    @Autowired
    public TeacherPostHandler(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    protected Service<Teacher, Long> service() {
        return teacherService;
    }
}
