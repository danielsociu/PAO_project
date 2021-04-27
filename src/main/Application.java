package main;

import models.*;
import models.Class;
import services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Application {
    public static void main(String[] args) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SchoolService schoolService = new SchoolService();
        ClassService classService = new ClassService();
        School school = new School.Builder()
                .withName("Base School")
                .build();

        schoolService.addClass(
                school,
                new Class.Builder()
                .withYear("11")
                .withYearPeriod("2020-2021")
                .withLetter("A")
                .withProgram(new Program("Informatics", 4))
                .withCatalogue(new Catalogue())
                .build()
        );

        Scanner commandInput = new Scanner(System.in);
        while (true) {
            System.out.println("Enter where do you want to make a change: catalogue, class, school; or exit");
            String place = commandInput.nextLine();
            switch (place) {
                case "catalogue":
                    System.out.println("Enter the class current year:");
                    String year = commandInput.nextLine();
                    System.out.println("Enter the class letter:");
                    String letter = commandInput.nextLine();
                    Class myClass = classService.getCurrentClass(year, letter);
                    System.out.println("What action do you want to do: add, edit, delete, show");
                    String action = commandInput.nextLine();
                    System.out.println(action + " a mark or absence?");
                    String target = commandInput.nextLine();
                    if (target.equals("mark")) {
                        switch (action) {
                            case "add":
                                classService.addGradeInteractive(commandInput, myClass);
                                break;
                            case "show":
                                classService.showGrades(myClass);
                                break;
                            case "edit":
                                classService.changeGradeInteractive(commandInput, myClass, "update");
                                break;
                            case "delete":
                                classService.changeGradeInteractive(commandInput, myClass, "delete");
                                break;
                        }
                    } else if (target.equals("absence")) {
                        switch (action) {
                            case "add":
                                classService.addAbsenceInteractive(commandInput, myClass);
                                break;
                            case "show":
                                classService.showAbsences(myClass);
                                break;
                            case "edit":
                                classService.changeAbsenceInteractive(commandInput, myClass, "update");
                                break;
                            case "delete":
                                classService.changeAbsenceInteractive(commandInput, myClass, "delete");
                                break;
                        }
                    }
                    break;
                case "class":

                    break;
            }
            break;
        }

        schoolService.showSchool(school);
        Class workingClass = school.getClasses().get(0);
        try {
            classService.addStudent(
                    workingClass,
                    new Student(
                            "Richard",
                            "Brown",
                            dateFormatter.parse("1/1/2004"),
                            "50010211",
                            workingClass
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            classService.addSubject(
                    workingClass,
                    new Subject(
                            "Informatics",
                            "Computer Science"
                    ),
                    new Teacher(
                            "David",
                            "Rockefeller",
                            dateFormatter.parse("1/1/2004"),
                            "50010211"
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // TODO: Change grade constructor to builder constructor
        // classService.addGrade(
        //         workingClass,
        //         new Grade(
        //                 7.6,
        //                 new Date(),
        //                 "Test",
        //                 workingClass.getStudents().get(0),
        //                 workingClass.getSubjects().get(0), // Helper kkkPair
        //                 workingClass.getSubjects().keySet().
        //         )
        // );

        schoolService.showSchool(school);
    }
}

