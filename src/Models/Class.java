package Models;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private String year;
    private String schoolYear;
    private String letter;
    private Catalogue catalogue;
    private List<Student> students;
    private List<Subject> subjects;

    public Class (String year, String schoolYear, String letter) {
        this.year = year;
        this.schoolYear = schoolYear;
        this.letter = letter;
        this.students = new ArrayList<Student>();
        this.subjects = new ArrayList<Subject>();
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getSchoolYear() {
        return schoolYear;
    }
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
    public String getLetter() {
        return letter;
    }
    public void setLetter() {
        this.letter = letter;
    }
    public List<Student> getStudents() {
        return students;
    }
    public void addStudent(Student student) {
        this.students.add(student);
    }
}
