package by.it.academy.adorop.model;

import by.it.academy.adorop.model.users.Teacher;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"teacher_id", "title"}))
@Cacheable
public class Course implements Serializable {

    private static final long serialVersionUID = 6986606233112251779L;

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "${message.notEmpty}")
    @Column(nullable = false)
    private String title;
    @Lob
    private String description;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return !(title != null ? !title.equals(course.title) : course.title != null) && !(teacher != null ? !teacher.equals(course.teacher) : course.teacher != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}
