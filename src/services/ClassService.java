package services;

import models.*;
import models.Class;

import java.util.*;
import java.util.stream.Collectors;

public class ClassService {
    private static ClassService classService = new ClassService();

    private ClassService() { }

    public static ClassService getClassService() {
        return classService;
    }

    public void addGrade(Class schoolClass, Grade grade) {
        schoolClass.getCatalogue().getGrades().add(grade);

        // Here we make sure the grades from catalogue are sorted
        schoolClass.getCatalogue().getGrades().sort(Comparator.comparingDouble(Grade::getScore));

        String message = "Grade " + grade +
                " has been added to class " + schoolClass;
        System.out.println(message);
    }

    public Class getCurrentClass(String year, String letter) {
        School school = School.getSchool();
        return school.getClasses()
                .stream()
                .filter(cls -> cls.getYearPeriod().split("-")[1]
                        .equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))))
                .filter(cls -> (cls.getYear().equals(year) && cls.getLetter().equals(letter)))
                .findFirst()
                .orElse(null);
    }

    public void addStudent(Class schoolClass, Student student) {
        SchoolService schoolService = SchoolService.getSchoolService();
        schoolService.addStudent(student);
        schoolClass.getStudents().add(student);

        String message = "Student " + student +
                " has been added to class " + schoolClass;
        System.out.println(message);
    }

    public void addSubject(Class schoolClass, Subject subject, Teacher teacher) {
        schoolClass.getSubjects().put(subject, teacher);

        String message = "Teacher " + teacher + " is now teaching " +
                subject + " in class " + schoolClass;
        System.out.println(message);
    }

    public void showGrades(Class schoolClass) {
        List<Grade> grades = schoolClass.getCatalogue().getGrades();
        if (grades.size() > 0) {
            schoolClass.getCatalogue().getGrades().forEach(System.out::println);
        }
    }

    public void showAbsences(Class schoolClass) {
        List<Absence> absences = schoolClass.getCatalogue().getAbsences();
        if (absences.size() > 0) {
            absences.forEach(System.out::println);
        } 
    }

    public void showClass(Class schoolClass) {
        System.out.println(schoolClass);
    }

    public void editClassInteractive(Scanner in, Class schoolClass) {
        School school = School.getSchool();
        System.out.println("You can edit one of the fields: year, yearPeriod, letter or add a program/student/subject");
        String option = in.nextLine();
        if ("year yearPeriod letter".contains(option)) {
            System.out.println("Enter the " + option);
            String choice = in.nextLine();
            switch (option) {
                case "year":
                    schoolClass.setYear(choice);
                    break;
                case "yearPeriod":
                    schoolClass.setYearPeriod(choice);
                    break;
                case "letter":
                    schoolClass.setLetter(choice);
                    break;
            }
        } else {
            int position;
            switch (option) {
                case "program":
                    List<Program> programs = school.getPrograms();
                    printList(programs);
                    System.out.println("Choose the program's number");
                    position = Integer.parseInt(in.nextLine());
                    schoolClass.setProgram(programs.get(position));
                    break;
                case "student":
                    List<Student> students = school.getStudents();
                    System.out.println("Enter the student's name:");
                    String studentName = in.nextLine();
                    List<Student> studentsNamed = students.stream()
                            .filter(std -> (studentName.contains(std.getFirstName())
                                        && studentName.contains(std.getLastName())))
                            .collect(Collectors.toList());
                    printList(students);
                    System.out.println("Choose the student:");
                    position = Integer.parseInt(in.nextLine());
                    schoolClass.getStudents().add(studentsNamed.get(position));
                    break;
                case "subject":
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
                    printList(teachers);
                    System.out.println("Choose the teacher:");
                    position = Integer.parseInt(in.nextLine());
                    schoolClass.getSubjects().put(mySubject, teachersNamed.get(position));
                    break;
            }
        }
    }

    public void addGradeInteractive(Scanner in, Class schoolClass) {
        System.out.println("Enter subject:");
        String subjectInput = in.nextLine();
        Subject subject = null;
        Teacher teacher = null;
        for (Subject key : schoolClass.getSubjects().keySet()) {
            if (key.getName().equals(subjectInput)) {
                subject = key;
                teacher = schoolClass.getSubjects().get(key);
                break;
            }
        }
        System.out.println("Enter student name:");
        String studentName = in.nextLine();
        Student student = schoolClass.getStudents().stream()
                .filter(stud -> (studentName.contains(stud.getFirstName())
                        && studentName.contains(stud.getLastName())))
                .findFirst()
                .orElse(null);
        System.out.println("Enter evaluation method:");
        String evaluationMethod = in.nextLine();
        System.out.println("Enter score:");
        double score = Double.parseDouble(in.nextLine());
        List<Grade> grades = schoolClass.getCatalogue().getGrades();
        grades.add(
                new Grade.Builder()
                        .withSubject(subject)
                        .withTeacher(teacher)
                        .withStudent(student)
                        .withEvaluationMethod(evaluationMethod)
                        .withScore(score)
                        .withDate(new Date())
                        .build()
        );
    }

    public void changeGradeInteractive(Scanner in, Class schoolClass, String operation) {
        System.out.println("Enter subject:");
        String subjectInput = in.nextLine();
        Subject subject = null;
        Teacher teacher = null;
        for (Subject key : schoolClass.getSubjects().keySet()) {
            if (key.getName().equals(subjectInput)) {
                subject = key;
                teacher = schoolClass.getSubjects().get(key);
                break;
            }
        }
        System.out.println("Enter student name:");
        String studentName = in.nextLine();
        Student student = schoolClass.getStudents().stream()
                .filter(stud -> (studentName.contains(stud.getFirstName())
                        && studentName.contains(stud.getLastName())))
                .findFirst()
                .orElse(null);
        Subject finalSubject = subject;
        Teacher finalTeacher = teacher;
        List<Grade> grades = schoolClass.getCatalogue().getGrades().stream()
                .filter(grade -> (grade.getSubject().equals(finalSubject)
                        && grade.getTeacher().equals(finalTeacher)
                        && grade.getStudent().equals(student)))
                .collect(Collectors.toList());
        printList(grades);
        if (operation.toLowerCase() == "edit") {
            System.out.println("Enter grade to be updated:");
            int updated = Integer.parseInt(in.nextLine());
            System.out.println("Enter new score:");
            double newGrade = Double.parseDouble(in.nextLine());
            grades.get(updated).setScore(newGrade);
        } else if (operation.toLowerCase() == "delete"){
            System.out.println("Enter grade to be deleted:");
            int deleted = Integer.parseInt(in.nextLine());
            schoolClass.getCatalogue().getGrades().remove(grades.get(deleted));
        }
    }

    public void addAbsenceInteractive(Scanner in, Class schoolClass) {
        System.out.println("Enter subject:");
        String subjectInput = in.nextLine();
        Subject subject = null;
        Teacher teacher = null;
        for (Subject key : schoolClass.getSubjects().keySet()) {
            if (key.getName().equals(subjectInput)) {
                subject = key;
                teacher = schoolClass.getSubjects().get(key);
                break;
            }
        }
        System.out.println("Enter student name:");
        String studentName = in.nextLine();
        Student student = schoolClass.getStudents().stream()
                .filter(stud -> (studentName.contains(stud.getFirstName())
                        && studentName.contains(stud.getLastName())))
                .findFirst()
                .orElse(null);
        List<Absence> absences = schoolClass.getCatalogue().getAbsences();
        System.out.println("Motivated true/false:");
        boolean motivated = Boolean.parseBoolean(in.nextLine());
        absences.add(
                new Absence.Builder()
                        .withSubject(subject)
                        .withTeacher(teacher)
                        .withStudent(student)
                        .withDate(new Date())
                        .withMotivated(motivated)
                        .build()
        );
    }

    public void changeAbsenceInteractive(Scanner in, Class schoolClass, String operation) {
        System.out.println("Enter subject:");
        String subjectInput = in.nextLine();
        Subject subject = null;
        Teacher teacher = null;
        for (Subject key : schoolClass.getSubjects().keySet()) {
            if (key.getName().equals(subjectInput)) {
                subject = key;
                teacher = schoolClass.getSubjects().get(key);
                break;
            }
        }
        System.out.println("Enter student name:");
        String studentName = in.nextLine();
        Student student = schoolClass.getStudents().stream()
                .filter(stud -> (studentName.contains(stud.getFirstName())
                        && studentName.contains(stud.getLastName())))
                .findFirst()
                .orElse(null);
        Subject finalSubject = subject;
        Teacher finalTeacher = teacher;
        List<Absence> absences = schoolClass.getCatalogue().getAbsences().stream()
                .filter(grade -> (grade.getSubject().equals(finalSubject)
                        && grade.getTeacher().equals(finalTeacher)
                        && grade.getStudent().equals(student)))
                .collect(Collectors.toList());
        printList(absences);
        if (operation.toLowerCase() == "edit") {
            System.out.println("Enter absence to be updated:");
            int updated = Integer.parseInt(in.nextLine());
            System.out.println("Enter new absence value:");
            boolean newMotivated = Boolean.parseBoolean(in.nextLine());
            absences.get(updated).setMotivated(newMotivated);
        } else if (operation.toLowerCase() == "delete"){
            System.out.println("Enter absence to be deleted:");
            int deleted = Integer.parseInt(in.nextLine());
            schoolClass.getCatalogue().getAbsences().remove(absences.get(deleted));
        }
    }

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }
}
