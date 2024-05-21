import model.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("demoDb");
        EntityManager em = factory.createEntityManager();
        boolean flag = true;
        while (flag) {
            System.out.print("#1 Instantiate database\n" +
                             "#2 Student lookup\n" +
                             "#3 Register for a course\n" +
                             "#4 Exit\n" +
                             "Enter a number: ");
            int input = sc.nextInt();
            if (input == 1) {
                Instantiate.instantiateDB(em);
            } else if (input == 2) {
                studentLookup(em);
            } else if (input == 3) {
                register(em);
            } else {
                System.exit(0);
            }
        }
        em.close();
        sc.close();
    }

    private static void studentLookup(EntityManager em){
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a student name: ");
            String name = sc.nextLine();
            var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE " + "s.name = ?1", Student.class);
            namedStudent.setParameter(1, name);
        try {
            Student s = namedStudent.getSingleResult();
            System.out.println("Your requested Student " + s.getName());
            Set<Transcript> t = s.getGrades();
            List<LocalDate> dates = new ArrayList<>();
            for (Transcript tr : t)
                dates.add(tr.getSection().getSemester().getStartDate());
            Collections.sort(dates);
            int x = 0;
            for (Transcript tr : t){
                if(tr.getSection().getSemester().getStartDate() == dates.get(x))
                    System.out.println(tr);
                x += 1;
            }

            System.out.println("Student GPA: " + s.getGpa());

            } catch (NoResultException e) {
                System.out.println("Student with name: '" + name + "' not found.");
            }
    }

    public static void register(EntityManager em) {
        Scanner sc = new Scanner(System.in);
        Semester semester = null;
        String sem;
        System.out.println("Enter a Semester: ");
        sem = sc.nextLine();

        var selectSem = em.createQuery("SELECT s FROM SEMESTERS s WHERE s.title = ?1", Semester.class);
        selectSem.setParameter(1, sem);
        try {
            semester = selectSem.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Semester with name: '" + sem + "' not found.");
        }

        Student student = null;
        System.out.println("Please enter a student name: ");
        String name = sc.nextLine();
        var namedStudent = em.createQuery("SELECT s FROM STUDENTS s WHERE " + "s.name = ?1", Student.class);
        namedStudent.setParameter(1, name);
        try {
             student = namedStudent.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Student with name: '" + name + "' not found.");
        }
        Section sec = null;
        System.out.println("Enter a course section EX: CECS 277-05");
        String section = sc.nextLine();
        String[] parsed = section.split("[ -]");
        String abbrev = parsed[0];
        String courseNum = parsed[1];
        int sectionNum = Integer.parseInt(parsed[2]);
        var selectSection = em.createQuery("SELECT s from SECTIONS s " + "JOIN s.course course JOIN course.department department " +
                "WHERE department.abbreviation = ?1 AND course.number = ?2 AND s.sectionNumber = ?3", Section.class);
        selectSection.setParameter(1, abbrev);
        selectSection.setParameter(2, courseNum);
        selectSection.setParameter(3, (byte)sectionNum);
        try {
            sec = selectSection.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Section: '" + sec + "' not found.");
        }

        em.getTransaction().begin();
        if(student.registerForSection(sec) == Student.RegistrationResult.SUCCESS);
            em.getTransaction().commit();
    }

}