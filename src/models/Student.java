package models;

import helpers.FileWritable;

import java.util.Date;
import java.util.Objects;

public class Student extends Person implements FileWritable {
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

    @Override
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + super.toCSVString()
                + ',' + String.valueOf(studentClass.hashCode()))   ;
    }

    @Override
    public String getFileName() {
        return "students";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " pid: " + super.getPid();
    }
}
