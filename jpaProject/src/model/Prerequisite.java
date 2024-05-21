package model;

import jakarta.persistence.*;

@Entity(name = "PREREQUISITES")
public class Prerequisite
{
    @Id
    @Column
    private char minimumGrade;

    @Id
    @ManyToOne
    @JoinColumn
    private Course preq;

    @Id
    @ManyToOne
    @JoinColumn
    private Course preqFor;

    public Prerequisite() {
    }

    public Prerequisite(char minimumGrade, Course preq, Course preqFor) {
        this.minimumGrade = minimumGrade;
        this.preq = preq;
        this.preqFor = preqFor;
        preqFor.getPrerequisites().add(this);
    }

    public char getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(char minimumGrade) {this.minimumGrade = minimumGrade;}

    public Course getPreq() { return preq; }

    public void setPreq(Course preq) {this.preq = preq;}

    public Course getPreqFor() { return preqFor; }

    public void setPreqFor(Course preqFor) {this.preqFor = preqFor;}
}
