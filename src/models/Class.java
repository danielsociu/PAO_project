package models;

import helpers.FileWritable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Class implements FileWritable {
    private String year;
    private String yearPeriod;
    private String letter;
    private Program program;
    private Catalogue catalogue;
    private List<Student> students;
    private HashMap<Subject, Teacher> subjects;

    private Class () {}

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYearPeriod() {
        return yearPeriod;
    }

    public void setYearPeriod(String yearPeriod) {
        this.yearPeriod = yearPeriod;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public List<Student> getStudents() {
        if (students == null) {
            return null;
        }
        return new ArrayList<>(students);
    }

    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public HashMap<Subject, Teacher> getSubjects() {
        if (subjects == null) {
            return null;
        }
        return new HashMap<>(subjects);
    }

    public void setSubjects(HashMap<Subject, Teacher> subjects) {
        this.subjects = new HashMap<>(subjects);
    }

    @Override
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + year + ',' + letter + ','
                + yearPeriod + ',' + String.valueOf(program.hashCode()));
    }

    @Override
    public String getFileName() {
        return "classes";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Class)) {
            return false;
        }
        Class myClass = (Class) o;
        return Objects.equals(year, myClass.getYear())&&
                Objects.equals(letter, myClass.getLetter()) &&
                Objects.equals(yearPeriod, myClass.getYearPeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, letter, yearPeriod);
    }

    @Override
    public String toString() {
        return "Class " + year + '-' + letter + ' ' + yearPeriod;
    }

    public static class Builder {
        private Class schoolClass = new Class();

        public Class.Builder withYear(String year) {
            schoolClass.setYear(year);
            return this;
        }
        public Class.Builder withYearPeriod(String yearPeriod) {
            schoolClass.setYearPeriod(yearPeriod);
            return this;
        }
        public Class.Builder withLetter(String letter) {
            schoolClass.setLetter(letter);
            return this;
        }
        public Class.Builder withProgram(Program program) {
            schoolClass.setProgram(program);
            return this;
        }
        public Class.Builder withCatalogue(Catalogue catalogue) {
            schoolClass.setCatalogue(catalogue);
            return this;
        }
        public Class.Builder withStudents(List<Student> students) {
            schoolClass.setStudents(students);
            return this;
        }
        public Class.Builder withSubjects(HashMap<Subject, Teacher> subjects) {
            schoolClass.setSubjects(subjects);
            return this;
        }
        public Class build() {
            if (this.schoolClass.getSubjects() == null) {
                this.schoolClass.setSubjects(new HashMap<Subject, Teacher>());
            }
            if (this.schoolClass.getStudents() == null) {
                this.schoolClass.setStudents(new ArrayList<Student>());
            }
            if (this.schoolClass.getCatalogue() == null) {
                this.schoolClass.setCatalogue(new Catalogue());
            }
            return this.schoolClass;
        }
    }
}
