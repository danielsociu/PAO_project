package services;

import models.*;
import models.Class;

import java.util.Iterator;
import java.util.List;

public class SchoolService {
    private ClassService classService = new ClassService();
    public void addClass(School school, Class schoolClass) {
        school.getClasses().add(schoolClass);

        String message = "Class " + schoolClass.getYear() + '-' + schoolClass;
        System.out.println(message);
    }

    public void showSchool(School school) {
        System.out.println(school);
        System.out.println("Has classes:");
        Iterator iter = school.getClasses().iterator();
        while (iter.hasNext()) {
            Class schoolClass = (Class) iter.next();
            System.out.println(schoolClass);
            System.out.println("Has marks:");
            classService.showGrades(schoolClass);
        }
    }
}
