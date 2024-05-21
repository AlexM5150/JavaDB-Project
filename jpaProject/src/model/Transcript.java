package model;

import jakarta.persistence.*;

@Entity(name = "TRANSCRIPTS")
public class Transcript
{
    @Id
    @Column(length = 2)
    private String gradeEarned;

    @Id
    @ManyToOne
    @JoinColumn(name = "studentNum")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "SECTION_ID")
    private Section section;

    public Transcript() {
    }

    public Transcript(String gradeEarned, Student student, Section section) {
        this.gradeEarned = gradeEarned;
        this.student = student;
        this.section = section;
    }

    public String getGradeEarned() { return gradeEarned; }

    public void setGradeEarned(String gradeEarned) { this.gradeEarned = gradeEarned; }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }

    public Section getSection() { return section; }

    public void setSection(Section section) { this.section = section; }

    @Override
    public String toString() {
        String course = section.getCourse().getDepartment().getAbbreviation();
        String courseNum = section.getCourse().getNumber();
        String semester = section.getSemester().getTitle();
        return course + " " + courseNum +  ", " + semester + ". Grade earned: " + gradeEarned;
    }
}