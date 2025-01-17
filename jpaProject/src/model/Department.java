package model;

import jakarta.persistence.*;
import java.util.*;

@Entity(name = "DEPARTMENTS")
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int DEPARTMENT_ID;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 8, nullable = false)
    private String abbreviation;

    @OneToMany(mappedBy = "department")
    private List<Course> courses;

    public Department() {
    }

    public Department(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.courses = new ArrayList<>();
    }

    public int getDepartmentID() { return DEPARTMENT_ID; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Course> getCourses() { return courses; }

    public void setCourses(List<Course> courses) { this.courses = courses; }

    public void addCourse(Course c) {this.courses.add(c); c.setDepartment(this); }
}