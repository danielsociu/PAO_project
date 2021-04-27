package models;

import helpers.Pair;

import java.util.*;

public class Teacher extends Person {
    private HashMap<Class, Subject> classes;

    public Teacher(String firstName, String lastName, Date birthDate, String pid) {
        super(firstName, lastName, birthDate, pid);
        this.classes = new HashMap<Class, Subject>();
    }

    public HashMap<Class, Subject> getClasses() {
        return classes;
    }

    // public void addClasses(Class schoolClass, Subject subject) {
    //     this.classes.add(new Pair<Class, Subject> (schoolClass, subject));
    // }

    public void setClasses(HashMap<Class, Subject> classes) {
        this.classes = classes;
    }
}
