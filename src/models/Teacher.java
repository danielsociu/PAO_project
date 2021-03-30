package models;

import helpers.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Teacher extends Person {
    private List<Pair<Class, Subject>> classes;

    public Teacher(String firstName, String lastName, Date birthDate, String pid) {
        super(firstName, lastName, birthDate, pid);
        this.classes = new ArrayList<Pair<Class, Subject>>();
    }

    public List<Pair<Class, Subject>> getClasses() {
        return classes;
    }

    // public void addClasses(Class schoolClass, Subject subject) {
    //     this.classes.add(new Pair<Class, Subject> (schoolClass, subject));
    // }

    public void setClasses(List<Pair<Class, Subject>> classes) {
        this.classes = classes;
    }
}
