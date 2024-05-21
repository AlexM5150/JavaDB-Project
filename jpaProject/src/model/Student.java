package model;

import jakarta.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Entity(name = "STUDENTS")
public class Student{
    public enum RegistrationResult{ SUCCESS, ALREADY_PASSED, ENROLLED_IN_SECTION, NO_PREREQUISITES, ENROLLED_IN_ANOTHER, TIME_CONFLICT };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int studentNum;

    //Student IDs are 9 digits from 0 to 9 fitting the limit of an int which fits 10 digits.
    @Column(unique = true, nullable = false)
    private int studentID;

    @Column(length = 128, nullable = false)
    private String name;

    @OneToMany(mappedBy = "student")
    private Set<Transcript> grades;

    @ManyToMany(mappedBy = "students")
    private Set<Section> sections;

    public Student(){
    }

    public Student(int studentID, String name){
        this.studentID = studentID;
        this.name = name;
        this.grades = new HashSet<>();
        this.sections = new HashSet<>();
    }

    public int getStudentNum() { return studentNum; }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Transcript> getGrades() { return grades; }

    public void setGrades(Set<Transcript> grades) { this.grades = grades; }

    public Set<Section> getSections() { return sections; }

    public void setSections(Set<Section> sections) { this.sections = sections; }

    public void addSection(Section s) { this.sections.add(s); s.getStudents().add(this); }

    public double getGpa(){
        double gradePoints = 0;
        int numUnits = 0;
        for(Transcript t : grades){
            byte units = t.getSection().getCourse().getUnits();
            numUnits += units;
            switch (t.getGradeEarned()){
                case "A" -> gradePoints += 4 * units;
                case "B" -> gradePoints += 3 * units;
                case "C" -> gradePoints += 2 * units;
                case "D" -> gradePoints += units;
            }
        }
        return gradePoints/numUnits;
    }

    public RegistrationResult registerForSection(Section s){
        Course c = s.getCourse();
        List<Prerequisite> prereqs = new ArrayList<>(c.getPrerequisites());

        for (Transcript t : this.grades){
            Course c1 = t.getSection().getCourse();
            char grade = t.getGradeEarned().charAt(0);

            // #1 Already C or better
            if (c.getCourseID() == c1.getCourseID() && grade <= 'C')
                return RegistrationResult.ALREADY_PASSED;
            // remove from list if preq not met
            if (!prereqs.isEmpty())
                prereqs.removeIf(p -> p.getPreq().getCourseID() == c1.getCourseID() && grade <= p.getMinimumGrade());
        }

        // #3 Prerequisites not met
        if (!prereqs.isEmpty())
            return RegistrationResult.NO_PREREQUISITES;

        for (Section sec : sections) {
            if (c.getCourseID() == sec.getCourse().getCourseID()){
                // #2 Enrolled in sec already
                if (s.getSectionNumber() == sec.getSectionNumber())
                    return RegistrationResult.ENROLLED_IN_SECTION;
                // #4 Enrolled different sec of course
                return RegistrationResult.ENROLLED_IN_ANOTHER;
            }
                // #5 Time Conflict
                TimeSlot t1 = s.getTimeSlot();
                TimeSlot t2 = sec.getTimeSlot();
                if (t1.getDaysOfWeek() == t2.getDaysOfWeek()){
                    if ( t1.getStartTime().equals(t2.getStartTime()) || t1.getEndTime().equals(t2.getEndTime()) ||
                            t1.getStartTime().until(t2.getStartTime(), ChronoUnit.MINUTES) <= 1 ||
                            t1.getEndTime().until(t2.getEndTime(), ChronoUnit.MINUTES) <= 1)
                        return RegistrationResult.TIME_CONFLICT;
                }
            }

        //mutate sets
        addSection(s);
        System.out.println("Successfully registered section.");
        return RegistrationResult.SUCCESS;
    }
}