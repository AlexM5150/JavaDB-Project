package model;

import jakarta.persistence.*;

import java.util.*;

@Entity(name = "COURSES")
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"DEPARTMENT_ID", "number"})
)
public class Course
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int COURSE_ID;

    @Column(length = 8, nullable = false)
    private String number;

    @Column(length = 64, nullable = false)
    private String title;

    //Courses are 3 to 4 units there are 126 units req fitting in the max 127 of a byte
    @Column(nullable = false)
    private byte units;

    @OneToMany(mappedBy = "preq")
    private Set<Prerequisite> prerequisites;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;


    public Course() {
    }

    public Course(String number, String title, byte units, Department d) {
        this.number = number;
        this.title = title;
        this.units = units;
        this.addDepartment(d);
        this.prerequisites = new HashSet<>();
    }

    public int getCourseID() { return COURSE_ID; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getUnits() { return units; }

    public void setUnits(byte units) { this.units = units; }

    public Set<Prerequisite> getPrerequisites() { return prerequisites; }

    public void setPrerequisites(Set<Prerequisite> prerequisites) { this.prerequisites = prerequisites; }

    public Department getDepartment() { return department; }

    public void setDepartment(Department d) { this.department = d; }

    public void addDepartment(Department d) { d.addCourse(this); }
}
