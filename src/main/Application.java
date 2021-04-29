package main;

import models.*;
import models.Class;
import services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;


public class Application {
    public static void main(String[] args) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SchoolService schoolService =  SchoolService.getSchoolService();
        ClassService classService = ClassService.getClassService();
        CatalogueService catalogueService = CatalogueService.getCatalogueService();
        School school = School.getSchool(); // School is a singleton
        school.setName("Base School");

        schoolService.addClass(
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
            String year, letter, target;
            Class myClass;
            System.out.println("NOTE: To add students/subjects you need to edit the class");
            System.out.println("Enter where do you want to make a change: catalogue, class, school; or exit");
            String place = commandInput.nextLine();
            System.out.println("What action do you want to do: add, edit, delete, show");
            String action = commandInput.nextLine();
            switch (place) {
                case "catalogue":
                    System.out.println("Enter the class current year:");
                    year = commandInput.nextLine();
                    System.out.println("Enter the class letter:");
                    letter = commandInput.nextLine();
                    myClass = classService.getCurrentClass(year, letter);
                    System.out.println(action + " a mark or absence?");
                    target = commandInput.nextLine();
                    if (target.equals("mark")) {
                        switch (action) {
                            case "add":
                                catalogueService.addGradeInteractive(commandInput, myClass);
                                break;
                            case "show":
                                catalogueService.showGrades(myClass);
                                break;
                            case "edit":
                                catalogueService.changeGradeInteractive(commandInput, myClass, "edit");
                                break;
                            case "delete":
                                catalogueService.changeGradeInteractive(commandInput, myClass, "delete");
                                break;
                        }
                    } else if (target.equals("absence")) {
                        switch (action) {
                            case "add":
                                catalogueService.addAbsenceInteractive(commandInput, myClass);
                                break;
                            case "show":
                                catalogueService.showAbsences(myClass);
                                break;
                            case "edit":
                                catalogueService.changeAbsenceInteractive(commandInput, myClass, "edit");
                                break;
                            case "delete":
                                catalogueService.changeAbsenceInteractive(commandInput, myClass, "delete");
                                break;
                        }
                    }
                    break;
                case "class":
                    if (action.equals("add")) {
                        schoolService.addClassInteractive(commandInput);
                        break;
                    }
                    List<Class> classes = school.getClasses();
                    if (classes.size() == 0) {
                        System.out.println("You first need to add a class!");
                        schoolService.addClassInteractive(commandInput);
                        classes = school.getClasses();
                    }
                    for (Class cls: classes) {
                        System.out.println(cls);
                    }
                    System.out.println("Enter the class current year:");
                    year = commandInput.nextLine();
                    System.out.println("Enter the class letter:");
                    letter = commandInput.nextLine();
                    myClass = classService.getCurrentClass(year, letter);
                    switch (action) {
                        case "show":
                            classService.showClass(myClass);
                            break;
                        case "edit":
                            classService.editClassInteractive(commandInput, myClass);
                            break;
                        case "delete":
                            // Update to delete students/teachers from here and from school to delete a classs
                            classes = school.getClasses();
                            classes.remove(myClass);
                            school.setClasses(classes);
                            break;
                    }
                    break;
                case "school":
                    switch (action) {
                        case "show":
                            schoolService.showSchool();
                            break;
                        case "edit":
                            schoolService.editSchoolInteractive(commandInput);
                            break;
                        case "delete":
                            schoolService.deleteSchoolInteractive(commandInput);
                            break;
                        case "add":
                            schoolService.addSchoolInteractive(commandInput);
                            break;
                    }
            }
            break;
        }

        schoolService.showSchool();
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
        schoolService.showSchool();
    }
}

