package services;

import models.*;
import models.Class;

import java.util.*;
import java.util.stream.Collectors;

public class ClassService {
    private final static ClassService classService = new ClassService();

    private ClassService() { }

    public static ClassService getClassService() {
        return classService;
    }

    public Class getCurrentClass(String year, String letter) {
        School school = School.getSchool();
        return school.getClasses()
                .stream()
                .filter(cls -> {
                    String[] years = cls.getYearPeriod().split("-");
                    return Integer.parseInt(years[0]) <= Calendar.getInstance().get(Calendar.YEAR)
                            && Integer.parseInt(years[1]) >= Calendar.getInstance().get(Calendar.YEAR);
                })
                .filter(cls -> (cls.getYear().equals(year) && cls.getLetter().equalsIgnoreCase(letter)))
                .findFirst()
                .orElse(null);
    }

    public void showClass(Class schoolClass) {
        System.out.println(schoolClass);
    }

    public void addStudent(Class schoolClass, Student student) {
        List<Student> classStudents = schoolClass.getStudents();
        student.setStudentClass(schoolClass);
        classStudents.add(student);

        schoolClass.setStudents(classStudents);

        String message = "Student " + student +
                " has been added to class " + schoolClass;
        System.out.println(message);
    }

    public void addSubject(Class schoolClass, Subject subject, Teacher teacher) {
        schoolClass.getSubjects().put(subject, teacher);
        Set<Class> teacherClasses = teacher.getClasses();
        if (!teacherClasses.contains(schoolClass)) {
            teacherClasses.add(schoolClass);
            teacher.setClasses(teacherClasses);
        }
        HashMap<Subject, Teacher> classSubjects = schoolClass.getSubjects();
        classSubjects.put(subject,teacher);

        String message = "Teacher " + teacher + " is now teaching " +
                subject + " in class " + schoolClass;
        System.out.println(message);
    }

    public void addStudentToClassInteractive(Scanner in, Class schoolClass) {
        School school = School.getSchool();
        List<Student> students = school.getStudents();
        System.out.println("Enter the student's name:");
        String studentName = in.nextLine();
        List<Student> studentsNamed = students.stream()
                .filter(std -> (studentName.contains(std.getFirstName())
                        && studentName.contains(std.getLastName())))
                .collect(Collectors.toList());
        printList(studentsNamed);
        System.out.println("Choose the student:");
        int position = Integer.parseInt(in.nextLine());
        addStudent(schoolClass, studentsNamed.get(position));
    }

    public void addSubjectToClassInteractive(Scanner in, Class schoolClass) {
        School school = School.getSchool();
        List<Subject> subjects = school.getSubjects();
        System.out.println("Enter the subject's name:");
        String subjectName = in.nextLine().toLowerCase();
        Subject mySubject = subjects.stream()
                .filter(sub -> subjectName.contains(sub.getName().toLowerCase()))
                .findFirst()
                .orElse(null);
        List<Teacher> teachers = school.getTeachers();
        System.out.println("Enter the teacher's name:");
        String teacherName = in.nextLine();
        List<Teacher> teachersNamed = teachers.stream()
                .filter(std -> (teacherName.contains(std.getFirstName())
                        && teacherName.contains(std.getLastName())))
                .collect(Collectors.toList());
        printList(teachersNamed);
        System.out.println("Choose the teacher:");
        int position = Integer.parseInt(in.nextLine());
        addSubject(schoolClass, mySubject, teachersNamed.get(position) );
    }

    public void editClassProgramInteractive(Scanner in, Class schoolClass) {
        School school = School.getSchool();
        List<Program> programs = school.getPrograms();
        printList(programs);
        System.out.println("Choose the program's number");
        int position = Integer.parseInt(in.nextLine());
        schoolClass.setProgram(programs.get(position));
    }

    public void editClassInteractive(Scanner in, Class schoolClass) {
        School school = School.getSchool();
        System.out.println("You can edit one of the fields: year, yearPeriod, letter or add a program/student/subject");
        String option = in.nextLine();
        if ("year yearPeriod letter".contains(option)) {
            System.out.println("Enter the " + option);
            String answer = in.nextLine();
            switch (option) {
                case "year":
                    schoolClass.setYear(answer);
                    break;
                case "yearPeriod":
                    schoolClass.setYearPeriod(answer);
                    break;
                case "letter":
                    schoolClass.setLetter(answer);
                    break;
            }
        } else {
            int position;
            switch (option) {
                case "program":
                    editClassProgramInteractive(in, schoolClass);
                    break;
                case "student":
                    addStudentToClassInteractive(in, schoolClass);
                    break;
                case "subject":
                    addSubjectToClassInteractive(in, schoolClass);
                    break;
            }
        }
    }

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }
}
