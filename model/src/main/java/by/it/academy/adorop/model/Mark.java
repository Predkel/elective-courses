package by.it.academy.adorop.model;

import by.it.academy.adorop.model.users.Student;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class Mark implements Serializable {

    private static final long serialVersionUID = -1785702160286528224L;

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;
    @ManyToOne(fetch = FetchType.EAGER)
    private Course course;
//    @Pattern(regexp = "[0-9]|(10)", message = "should be a number from zero to ten")
    @Column
    private Integer value;

    public Mark() {
    }

    public Mark(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mark mark = (Mark) o;

        return !(student != null ? !student.equals(mark.student) : mark.student != null) && !(course != null ? !course.equals(mark.course) : mark.course != null);

    }

    @Override
    public int hashCode() {
        int result = student != null ? student.hashCode() : 0;
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", value=" + value +
                '}';
    }
}
