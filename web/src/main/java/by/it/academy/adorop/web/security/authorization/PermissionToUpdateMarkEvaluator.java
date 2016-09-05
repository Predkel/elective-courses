package by.it.academy.adorop.web.security.authorization;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("permissionToUpdateMarkEvaluator")
public class PermissionToUpdateMarkEvaluator implements PermissionEvaluator {
    private final CourseService courseService;

    @Autowired
    public PermissionToUpdateMarkEvaluator(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject.getClass() != Mark.class || !"update".equals(permission)) {
            return false;
        }
        Mark targetMark = (Mark) targetDomainObject;
        if (targetMark == null || targetMark.getCourse() == null || getCourseId(targetMark) == null) {
            return false;
        }
        return isTeacherOfCourseOfMark(authentication, targetMark);
    }

    private boolean isTeacherOfCourseOfMark(Authentication authentication, Mark targetMark) {
        return authentication.getPrincipal().equals(courseService.find(getCourseId(targetMark)).getTeacher());
    }

    private Long getCourseId(Mark targetMark) {
        return targetMark.getCourse().getId();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
