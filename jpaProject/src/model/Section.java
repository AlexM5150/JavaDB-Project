package model;

import jakarta.persistence.*;

import java.util.*;

@Entity(name = "SECTIONS")
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"SEMESTER_ID", "COURSE_ID", "sectionNumber"})
)
public class Section
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int SECTION_ID;

    //Sections are similar to courses and will fit the 127 limit of a byte .
    @Column(nullable = false)
    private byte sectionNumber;

    //Class sections can be larger than 127 so short is the best option since it will never reach its max but could pass the min.
    @Column(nullable = false)
    private short maxCapacity;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "TIMESLOT_ID")
    private TimeSlot timeslot;

    @ManyToOne
    @JoinColumn(name = "SEMESTER_ID")
    private Semester semester;

    @ManyToMany
    @JoinTable (
            name = "ENROLLEDSTUDENTS",
            joinColumns = @JoinColumn(name = "SECTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "studentNum")
    )
    private Set<Student> students;

    public Section() {
    }

    public Section(byte sectionNumber, short maxCapacity, Course course, Semester semester, TimeSlot timeslot) {
        this.sectionNumber = sectionNumber;
        this.maxCapacity = maxCapacity;
        this.students = new HashSet<>();
        this.course = course;
        this.addSemester(semester);
        this.timeslot = timeslot;
    }

    public int getSectionID() { return SECTION_ID; }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(byte sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(short maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public TimeSlot getTimeSlot() { return timeslot; }

    public void setTimeSlot(TimeSlot timeslot) { this.timeslot = timeslot; }

    public Semester getSemester() { return semester; }

    public void setSemester(Semester s) { this.semester = s; }

    public void addSemester(Semester s) { s.addSection(this); }

    public Set<Student> getStudents() { return students; }

    public void setStudents(Set<Student> s) { this.students = s; }

    public void addStudent(Student s) { s.addSection(this); }
}