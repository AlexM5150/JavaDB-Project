package model;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.*;

@Entity(name = "SEMESTERS")
public class Semester
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int SEMESTER_ID;

    @Column(length = 16, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @OneToMany(mappedBy = "semester")
    private List<Section> sections;

    public Semester() {
    }

    public Semester(String title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
        this.sections = new ArrayList<>();
    }

    public int getSemesterID() { return SEMESTER_ID; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void addSection(Section s) { this.sections.add(s); s.setSemester(this); }
}