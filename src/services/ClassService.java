package services;

import helpers.Pair;
import models.*;
import models.Class;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ClassService {

    public void addGrade(Class schoolClass, Grade grade) {
        schoolClass.getCatalogue().getGrades().add(grade);

        // Here we make sure the grades from catalogue are sorted
        Collections.sort(schoolClass.getCatalogue().getGrades(), new Comparator<Grade>() {
            @Override
            public int compare(Grade g1, Grade g2) {
                if (g1.getScore() < g2.getScore()) return -1;
                if (g1.getScore() > g2.getScore()) return 1;
                return 0;
            }
        });

        String message = "Grade " + grade +
                " has been added to class " + schoolClass;
        System.out.println(message);
    }

    public void addStudent(Class schoolClass, Student student) {
        schoolClass.getStudents().add(student);

        String message = "Student " + student +
                " has been added to class " + schoolClass;
        System.out.println(message);
    }

    public void addSubject(Class schoolClass, Subject subject, Teacher teacher ) {
        schoolClass.getSubjects().add(new Pair<Subject, Teacher>(subject, teacher));

        String message = "Teacher " + teacher + " is now teaching " +
                subject + " in class " + schoolClass;
        System.out.println(message);
    }

    public void showGrades(Class schoolClass) {
        Iterator iter = schoolClass.getCatalogue().getGrades().iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
