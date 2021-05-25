package models;

import helpers.FileWritable;

import java.util.*;

public class Teacher extends Person  implements FileWritable {
    private int idTeacher;
    private Set<Class> classes;

    public Teacher(String firstName, String lastName, Date birthDate, String pid) {
        super(firstName, lastName, birthDate, pid);
        this.classes = new HashSet<>();
    }

    public Teacher(String firstName, String lastName, Date birthDate, String pid, Set<Class> classes) {
        super(firstName, lastName, birthDate, pid);
        this.classes = new HashSet<>(classes);
    }

    public Teacher(int idTeacher, String firstName, String lastName, Date birthDate, String pid) {
        super(firstName, lastName, birthDate, pid);
        this.idTeacher = idTeacher;
        this.classes = new HashSet<>();
    }

    public Teacher(int idTeacher, String firstName, String lastName, Date birthDate, String pid, Set<Class> classes) {
        super(firstName, lastName, birthDate, pid);
        this.idTeacher = idTeacher;
        this.classes = new HashSet<>(classes);
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

    public int getIdTeacher() {
        return this.idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    @Override
    public String getFileName() {
        return "teachers";
    }

    @Override
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + super.toCSVString());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
