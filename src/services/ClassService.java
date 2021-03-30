package services;

import helpers.Pair;
import models.*;
import models.Class;

import java.util.Iterator;

public class ClassService {

    public void addGrade(Class schoolClass, Grade grade) {
        schoolClass.getCatalogue().getGrades().add(grade);

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
