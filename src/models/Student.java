package models;

import java.util.Date;

public class Student extends Person {
    Class studentClass;

    public Student(String firstName, String lastName, Date birthDate, String pid, Class studentClass) {
        super(firstName, lastName, birthDate, pid);
        this.studentClass = studentClass;
    }
    public Class getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }
}
