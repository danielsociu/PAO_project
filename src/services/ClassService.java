package services;

import models.*;
import models.Class;

import java.util.*;
import java.util.stream.Collectors;

public class ClassService {

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
        schoolClass.getCatalogue().getGrades().forEach(System.out::println);
    }

    public void showAbsences(Class schoolClass) {
        schoolClass.getCatalogue().getAbsences().forEach(System.out::println);
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
        for (int i = 0; i < grades.size(); i++) {
            System.out.println(i + ". " + grades.get(i).toString());
        }
        if (operation.toLowerCase() == "update") {
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
        for (int i = 0; i < absences.size(); i++) {
            System.out.println(i + ". " + absences.get(i).toString());
        }
        if (operation.toLowerCase() == "update") {
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
}
