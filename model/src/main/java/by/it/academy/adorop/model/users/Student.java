package by.it.academy.adorop.model.users;

import by.it.academy.adorop.model.Mark;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends User implements Serializable {

    private static final long serialVersionUID = -52174491949198584L;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Mark> marks = new HashSet<>();

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }

    @Override
    public String toString() {
        return "Student{" + super.toString();
    }
}
