package model;

import java.time.LocalTime;

import jakarta.persistence.*;

@Entity(name = "TIMESLOTS")
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"daysOfWeek", "startTime", "endTime"})
)
public class TimeSlot
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int TIMESLOT_ID;

    @Column(nullable = false)
    private byte daysOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public TimeSlot() {
    }

    public TimeSlot(byte daysOfWeek, LocalTime startTime, LocalTime endTime) {
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getTimeSlotID() { return TIMESLOT_ID; }

    public byte getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(byte daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}