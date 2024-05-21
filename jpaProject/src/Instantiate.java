import jakarta.persistence.EntityManager;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Instantiate {

    public static void instantiateDB(EntityManager em) {
            em.getTransaction().begin();

            List<Department> dept = initDepartments();
            List<Semester> sem = initSemesters();
            List<Course> courses = initCourses(dept);
            List<TimeSlot> ts = initTimeSlots();
            List<Section> sec = initSections(courses, sem, ts);
            List<Student> stu = initStudents();
            //List<Prerequisite> p = initPreqs(courses);
            //List<Transcript> tr = initTranscripts(stu, sec);
            initEnroll(stu, sec);

            for (Department d : dept)
                em.persist(d);
            for (Semester s : sem)
                em.persist(s);
            for (Course c : courses)
                em.persist(c);
            for (TimeSlot t : ts)
                em.persist(t);
            for (Section s : sec)
                em.persist(s);
            for (Student s : stu)
                em.persist(s);

            em.getTransaction().commit();
            em.getTransaction().begin();
            List<Prerequisite> p = initPreqs(courses);
            for (Prerequisite preq : p)
                em.persist(preq);
            em.getTransaction().commit();
            em.getTransaction().begin();
            List<Transcript> tr = initTranscripts(stu, sec);
            for (Transcript t : tr)
                em.persist(t);
            em.getTransaction().commit();


    }
    private static List<Department> initDepartments() {
        List<Department> d = new ArrayList<>();
        Department d1 = new Department("Computer Engineering and Computer Science", "CECS");
        d.add(d1);
        Department d2 = new Department("Italian Studies", "ITAL");
        d.add(d2);
        return d;
    }

    private static List<Student> initStudents() {
        List<Student> s = new ArrayList<>();
        Student s1 = new Student(123456789, "Naomi Nagata");
        s.add(s1);
        Student s2 = new Student(987654321, "James Holden");
        s.add(s2);
        Student s3 = new Student(555555555, "Amos Burton");
        s.add(s3);
        return s;
    }

    private static List<Semester> initSemesters() {
        List<Semester> s = new ArrayList<>();
        Semester s1 = new Semester("Spring 2021", LocalDate.of(2021, 1, 19));
        s.add(s1);
        Semester s2 = new Semester("Fall 2021", LocalDate.of(2021, 8, 17));
        s.add(s2);
        Semester s3 = new Semester("Spring 2022", LocalDate.of(2022, 1, 20));
        s.add(s3);
        return s;
    }

    private static List<Course> initCourses(List<Department> d) {
        List<Course> c = new ArrayList<>();
        Course c1 = new Course("174", "Introduction to Programming and Problem Solving", (byte)3, d.get(0));
        c.add(c1);
        Course c2 = new Course("274", "Data Structures", (byte)3, d.get(0));
        c.add(c2);
        Course c3 = new Course("277", "Object Oriented Application Programming", (byte) 3, d.get(0));
        c.add(c3);
        Course c4 = new Course("282", "Advanced C++", (byte)3, d.get(0));
        c.add(c4);
        Course c5 = new Course("101A", "Fundamentals of Italian", (byte)4, d.get(1));
        c.add(c5);
        Course c6 = new Course("101B", "Fundamentals of Italian", (byte)4, d.get(1));
        c.add(c6);
        return c;
    }

    private static List<TimeSlot> initTimeSlots() {
        List<TimeSlot> t = new ArrayList<>();
        TimeSlot t1 = new TimeSlot((byte)0b0101000, LocalTime.of(12, 30), LocalTime.of(13, 45));
        t.add(t1);
        TimeSlot t2 = new TimeSlot((byte)0b0010100, LocalTime.of(14, 0), LocalTime.of(15, 15));
        t.add(t2);
        TimeSlot t3 = new TimeSlot((byte)0b0101010, LocalTime.of(12, 0), LocalTime.of(12, 50));
        t.add(t3);
        TimeSlot t4 = new TimeSlot((byte)0b0000010, LocalTime.of(8, 0), LocalTime.of(10, 45));
        t.add(t4);
        return t;
    }

    private static List<Section> initSections(List<Course> c, List<Semester> sem, List<TimeSlot> t) {
        List<Section> sec = new ArrayList<>();
        Section sec1 = new Section((byte)1, (short)105, c.get(0), sem.get(0), t.get(0));
        sec.add(sec1);
        Section sec2 = new Section((byte)1, (short)140, c.get(1), sem.get(1), t.get(1));
        sec.add(sec2);
        Section sec3 = new Section((byte)3, (short)35, c.get(2), sem.get(1), t.get(3));
        sec.add(sec3);
        Section sec4 = new Section((byte)5, (short)35, c.get(3), sem.get(2), t.get(1));
        sec.add(sec4);
        Section sec5 = new Section((byte)1, (short)35, c.get(2), sem.get(2), t.get(0));
        sec.add(sec5);
        Section sec6 = new Section((byte)7, (short)35, c.get(3), sem.get(2), t.get(0));
        sec.add(sec6);
        Section sec7 = new Section((byte)1, (short)25, c.get(4), sem.get(2), t.get(0));
        sec.add(sec7);
        return sec;
    }

    private static List<Prerequisite> initPreqs(List<Course> c) {
        List<Prerequisite> p = new ArrayList<>();

        Prerequisite p1 = new Prerequisite('C', c.get(0), c.get(1));
        p.add(p1);
        Prerequisite p2 = new Prerequisite('C', c.get(0), c.get(2));
        p.add(p2);
        Prerequisite p3 = new Prerequisite('C', c.get(1), c.get(3));
        p.add(p3);
        Prerequisite p4 = new Prerequisite('C', c.get(2), c.get(3));
        p.add(p4);
        Prerequisite p5 = new Prerequisite('D', c.get(4), c.get(5));
        p.add(p5);
        return p;
    }

    private static void initEnroll(List<Student> s, List<Section> sec) {
        s.get(0).addSection(sec.get(3));
    }

    private static List<Transcript> initTranscripts(List<Student> s, List<Section> sec) {
        List<Transcript> t = new ArrayList<>();
        //Naomi
        Transcript t1 = new Transcript("A", s.get(0), sec.get(0));
        s.get(0).getGrades().add(t1);
        t.add(t1);
        Transcript t2 = new Transcript("A", s.get(0), sec.get(1));
        s.get(0).getGrades().add(t2);
        t.add(t2);
        Transcript t3 = new Transcript("A", s.get(0), sec.get(2));
        s.get(0).getGrades().add(t3);
        t.add(t3);

        //James
        Transcript t4 = new Transcript("C", s.get(1), sec.get(0));
        s.get(1).getGrades().add(t4);
        t.add(t4);
        Transcript t5 = new Transcript("C", s.get(1), sec.get(1));
        s.get(1).getGrades().add(t5);
        t.add(t5);
        Transcript t6 = new Transcript("C", s.get(1), sec.get(2));
        s.get(1).getGrades().add(t6);
        t.add(t6);

        //Amos
        Transcript t7 = new Transcript("C", s.get(2), sec.get(0));
        s.get(2).getGrades().add(t7);
        t.add(t7);
        Transcript t8 = new Transcript("B", s.get(2), sec.get(1));
        s.get(2).getGrades().add(t8);
        t.add(t8);
        Transcript t9 = new Transcript("D", s.get(2), sec.get(1));
        s.get(2).getGrades().add(t9);
        t.add(t9);
        return t;
    }
}