package by.it.academy.adorop.web.utils;

import by.it.academy.adorop.model.Course;
import by.it.academy.adorop.model.users.Teacher;

public class CourseSecurity {

    private CourseSecurity() {
    }

    public static boolean isTeacherOfTheCourse(Teacher teacher, Course course) {
        return course.getTeacher().equals(teacher);
    }
}
