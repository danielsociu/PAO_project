package services;

import models.*;
import models.Class;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Date;

public class SchoolService {
    private static SchoolService schoolService = new SchoolService();

    private SchoolService () {

    }

    public static SchoolService getSchoolService() {
        return schoolService;
    }


    public void addClass(School school, Class schoolClass) {
        school.getClasses().add(schoolClass);

        String message = "Class " + schoolClass.getYear() + '-' + schoolClass;
        System.out.println(message);
    }

    public void addClassInteractive(Scanner in) {
        School school = School.getSchool();
        System.out.println("Enter the year period yyyy-yyyy:");
        String yearPeriod = in.nextLine();
        System.out.println("Enter the year");
        String year = in.nextLine();
        System.out.println("Enter the letter");
        String letter = in.nextLine();
        List<Program> programs = school.getPrograms();
        for (int i = 0; i < programs.size(); i++) {
            System.out.println(i + ". " + programs.get(i));
        }
        System.out.println("Enter the program digit:");
        int program = Integer.parseInt(in.nextLine());

        this.addClass(school,
                new Class.Builder()
                        .withYearPeriod(yearPeriod)
                        .withYear(year)
                        .withLetter(letter)
                        .withProgram(programs.get(program))
                        .build()
        );
    }

    public void addStudent(Student student) {
        School school = School.getSchool();
        school.getStudents().add(student);
    }

    public void addTeacher(Teacher teacher) {
        School school = School.getSchool();
        school.getTeachers().add(teacher);
    }

    public void addProgram(Program program) {
        School school = School.getSchool();
        school.getPrograms().add(program);
    }

    public void addSubject(Subject subject) {
        School school = School.getSchool();
        school.getSubjects().add(subject);
    }

    public void editSchoolInteractive(Scanner in) {
        School school = School.getSchool();
        System.out.println("Enter the name of the school");
        String schoolName = in.nextLine();
        school.setName(schoolName);
    }

    public void deleteSchoolInteractive(Scanner in) {
        School school = School.getSchool();
        System.out.println("Enter what to delete class/student/teacher/program/subject");
        int choice;
        String elementDeleted = in.nextLine();
        switch (elementDeleted) {
            case "class":
                List<Class> classes = school.getClasses();
                printList(classes);
                System.out.println("Choose the class to delete:");
                choice = Integer.parseInt(in.nextLine());
                classes.remove(choice);
                break;
            case "student":
                List<Student> students = school.getStudents();
                System.out.println("Enter the name of the student:");
                String studentName = in.nextLine();
                List<Student> studentsNamed = students.stream()
                        .filter(stud -> studentName.contains(stud.getLastName())
                            && studentName.contains(stud.getFirstName()))
                        .collect(Collectors.toList());
                printList(studentsNamed);
                System.out.println("Choose the student to delete:");
                choice = Integer.parseInt(in.nextLine());
                students.remove(studentsNamed.get(choice));
                break;
            case "teacher":
                List<Teacher> teachers = school.getTeachers();
                System.out.println("Enter the name of the teacher:");
                String teacherName = in.nextLine();
                List<Teacher> teachersNamed = teachers.stream()
                        .filter(stud -> teacherName.contains(stud.getLastName())
                                && teacherName.contains(stud.getFirstName()))
                        .collect(Collectors.toList());
                printList(teachersNamed);
                System.out.println("Choose the teacher to delete:");
                choice = Integer.parseInt(in.nextLine());
                teachers.remove(teachersNamed.get(choice));
                break;
            case "program":
                List<Program> programs = school.getPrograms();
                printList(programs);
                System.out.println("Choose the program to delete:");
                choice = Integer.parseInt(in.nextLine());
                programs.remove(choice);
                break;
            case "subject":
                List<Subject> subjects = school.getSubjects();
                printList(subjects);
                System.out.println("Choose the subject to delete:");
                choice = Integer.parseInt(in.nextLine());
                subjects.remove(choice);
                break;
        }
    }

    public void addSchoolInteractive(Scanner in) throws ParseException {
        School school = School.getSchool();
        System.out.println("Enter what to add student/teacher/program/subject/class");
        String elementAdded = in.nextLine();
        String firstName = null;
        String lastName = null;
        String pid = null;
        Date date = null;
        if ("student/teacher".contains(elementAdded)) {
            System.out.println("Enter the " + elementAdded + " first name:");
            firstName = in.nextLine();
            System.out.println("Enter the " + elementAdded + " last name:");
            lastName = in.nextLine();
            System.out.println("Enter the " + elementAdded + " pid:");
            pid = in.nextLine();
            System.out.println("Enter the " + elementAdded + " birthdate(dd/mm/yyyy):");
            String dateString = in.nextLine();
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        }
        int choice;
        switch (elementAdded) {
            case "class":
                addClassInteractive(in);
                break;
            case "student":
                List<Class> classes = school.getClasses();
                printList(classes);
                System.out.println("Enter the student class index:");
                choice = Integer.parseInt(in.nextLine());
                Class studentClass = classes.get(choice);
                addStudent(new Student(
                            firstName,
                            lastName,
                            date,
                            pid,
                            studentClass
                            ));
                break;
            case "teacher":
                addTeacher(new Teacher(
                            firstName,
                            lastName,
                            date,
                            pid
                            ));
                break;
            case "program":
                System.out.println("Enter the program name:");
                String programName = in.nextLine();
                System.out.println("Enter the program duration (years):");
                int duration = Integer.parseInt(in.nextLine());
                addProgram(new Program(
                            programName,
                            duration
                            ));
                break;
            case "subject":
                System.out.println("Enter the subject name:");
                String subjectName = in.nextLine();
                System.out.println("Enter the subject domain:");
                String subjectDomain = in.nextLine();
                addSubject(new Subject(
                            subjectName,
                            subjectDomain
                            ));
                break;
        }
    }

    public void showSchool() {
        ClassService classService = ClassService.getClassService();
        School school = School.getSchool();
        System.out.println(school);
        System.out.println("Has classes:");
        for (Class schoolClass : school.getClasses()) {
            System.out.println(schoolClass);
            System.out.println("Has marks:");
            classService.showGrades(schoolClass);
        }
    }

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }
}
