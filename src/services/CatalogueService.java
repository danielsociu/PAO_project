package services;

import models.*;
import models.Class;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CatalogueService {
    private static final CatalogueService catalogueService = new CatalogueService();

    private CatalogueService() { }

    public static CatalogueService getCatalogueService() {
        return catalogueService;
    }

    public void addGradeInteractive(Scanner in, Class schoolClass) {
        System.out.println("Enter subject:");
        String subjectInput = in.nextLine();
        Subject subject = null;
        Teacher teacher = null;
        for (Subject key : schoolClass.getSubjects().keySet()) {
            if (key.getName().equalsIgnoreCase(subjectInput)) {
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
            if (key.getName().equalsIgnoreCase(subjectInput)) {
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
        if (operation.equalsIgnoreCase("edit")) {
            System.out.println("Enter grade to be updated:");
            int updated = Integer.parseInt(in.nextLine());
            System.out.println("Enter new score:");
            double newGrade = Double.parseDouble(in.nextLine());
            grades.get(updated).setScore(newGrade);
        } else if (operation.equalsIgnoreCase("delete")) {
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
            if (key.getName().equalsIgnoreCase(subjectInput)) {
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
            if (key.getName().equalsIgnoreCase(subjectInput)) {
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
        if (operation.equalsIgnoreCase("edit")) {
            System.out.println("Enter absence to be updated:");
            int updated = Integer.parseInt(in.nextLine());
            System.out.println("Enter new absence value:");
            boolean newMotivated = Boolean.parseBoolean(in.nextLine());
            absences.get(updated).setMotivated(newMotivated);
        } else if (operation.equalsIgnoreCase("delete")) {
            System.out.println("Enter absence to be deleted:");
            int deleted = Integer.parseInt(in.nextLine());
            schoolClass.getCatalogue().getAbsences().remove(absences.get(deleted));
        }
    }

    public void addGrade(Class schoolClass, Grade grade) {
        List<Grade> grades = schoolClass.getCatalogue().getGrades();
        grades.add(grade);

        // Here we make sure the grades from catalogue are sorted
        grades.sort(Comparator.comparingDouble(Grade::getScore));

        schoolClass.getCatalogue().setGrades(grades);
        String message = "Grade " + grade +
                " has been added to class " + schoolClass;
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

    private <T> void printList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". " + list.get(i));
        }
    }
}
