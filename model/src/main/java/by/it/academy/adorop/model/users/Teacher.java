package by.it.academy.adorop.model.users;

import by.it.academy.adorop.model.Course;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends User implements Serializable {

    private static final long serialVersionUID = -1041249766477934517L;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString();
    }

    public void addCourse(Course course) {
        course.setTeacher(this);
        courses.add(course);
    }
}
