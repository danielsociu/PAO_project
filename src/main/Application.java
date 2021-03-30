package main;

import models.*;
import models.Class;
import services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// TODO: Add a menu.

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
        classService.addGrade(
                workingClass,
                new Grade(
                        7.6,
                        new Date(),
                        "Test",
                        workingClass.getStudents().get(0),
                        workingClass.getSubjects().get(0).getR(), // Helper Pair
                        workingClass.getSubjects().get(0).getL()
                )
        );

        schoolService.showSchool(school);
    }
}

