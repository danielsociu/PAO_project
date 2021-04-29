package models;

import java.util.*;

public class Teacher extends Person {
    private Set<Class> classes;

    public Teacher(String firstName, String lastName, Date birthDate, String pid) {
        super(firstName, lastName, birthDate, pid);
        this.classes = new HashSet<>();
    }

    public Set<Class> getClasses() {
        return new HashSet<>(classes);
    }

    // public void addClasses(Class schoolClass, Subject subject) {
    //     this.classes.add(new Pair<Class, Subject> (schoolClass, subject));
    // }

    public void setClasses(Set<Class> classes) {
        this.classes = new HashSet<>(classes);
    }
}
