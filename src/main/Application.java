package main;

import models.*;
import models.Class;
import repository.SchoolRepository;
import services.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;


public class Application {
    public static void main(String[] args) {
        // Services
        RWService rwService = RWService.getRwService();
        SchoolService schoolService =  SchoolService.getSchoolService();
        ClassService classService = ClassService.getClassService();
        CatalogueService catalogueService = CatalogueService.getCatalogueService();
        // Repos
        SchoolRepository schoolRepository = new SchoolRepository();

        Scanner commandInput = new Scanner(System.in);

        System.out.println("Select the school:\n0 - new school");
        schoolRepository.listSchools();
        int schoolId = Integer.parseInt(commandInput.nextLine());

        School school = School.getSchool(); // School is a singleton
        if (schoolId == 0) {
            System.out.println("Enter the school name:");
            String schoolName = commandInput.nextLine();
            school.setName(schoolName);
            schoolRepository.addSchool(school);
        } else {
            try {
                schoolRepository.getSchoolId(schoolId);
            } catch (SQLException e) {
                e.printStackTrace();
            };
        }


        // rwService.readAllData(school);
        System.out.println();

        // File f = new File("potato/exemplu.txt");
        // Path path = Paths.get("csvDatabase/class");
        // System.out.println(f.getAbsolutePath());
        // System.out.println(path.toString());

        // schoolService.addClass(
        //         new Class.Builder()
        //         .withYear("11")
        //         .withYearPeriod("2020-2021")
        //         .withLetter("A")
        //         .withProgram(new Program("Informatics", 4))
        //         .withCatalogue(new Catalogue())
        //         .build()
        // );

        while (true) {
            String year, letter, target;
            int classIndex = 0;
            Class myClass;
            System.out.println("NOTE: To add students/subjects you need to edit the class");
            System.out.println("Enter where do you want to make a change: catalogue, class, school; or exit");
            String place = commandInput.nextLine();
            if (place.equals("exit")) {
                break;
            }
            System.out.println("What action do you want to do: add, edit, delete, show");
            String action = commandInput.nextLine();
            switch (place) {
                case "catalogue":
                    schoolService.printList(school.getClasses());
                    // System.out.println("Enter the class current year:");
                    // year = commandInput.nextLine();
                    // System.out.println("Enter the class letter:");
                    // letter = commandInput.nextLine();
                    // myClass = classService.getCurrentClass(year, letter);
                    classIndex = Integer.parseInt(commandInput.nextLine());
                    myClass = school.getClasses().get(classIndex);
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
                    // for (Class cls: classes) {
                    //     System.out.println(cls);
                    // }
                    // System.out.println("Enter the class current year:");
                    // year = commandInput.nextLine();
                    // System.out.println("Enter the class letter:");
                    // letter = commandInput.nextLine();
                    // myClass = classService.getCurrentClass(year, letter);
                    schoolService.printList(school.getClasses());
                    classIndex = Integer.parseInt(commandInput.nextLine());
                    myClass = school.getClasses().get(classIndex);
                    switch (action) {
                        case "show":
                            classService.showClass(myClass);
                            break;
                        case "edit":
                            classService.editClassInteractive(commandInput, myClass);
                            break;
                        case "delete":
                            classService.deleteClassInteractive(commandInput, myClass);
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
                    break;
            }
        }
        //schoolService.showSchool();
    }
}

