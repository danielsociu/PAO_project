package models;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
    private List<Grade> grades;
    private List<Absence> absences;

    public Catalogue(List<Grade> grades, List<Absence> absences) {
        this.grades = grades;
        this.absences = absences;
    }
    public Catalogue() {
        this.grades = new ArrayList<Grade>();
        this.absences = new ArrayList<Absence>();
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }
}
