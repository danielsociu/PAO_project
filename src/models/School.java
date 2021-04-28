package models;


import java.util.ArrayList;
import java.util.List;

public class School {
    private static School school = new School();
    private String name;
    private List<Class> classes;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Program> programs;
    private List<Subject> subjects;

    private School() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public static School getSchool() {
        return school;
    }

    @Override
    public String toString() {
        return "School: " + name;
    }

    public static class Builder {
        public Builder withName(String name) {
            school.setName(name);
            return this;
        }

        public Builder withClasses(List<Class> classes) {
            school.setClasses(classes);
            return this;
        }

        public Builder withTeachers(List<Teacher> teachers) {
            school.setTeachers(teachers);
            return this;
        }

        public Builder withStudents(List<Student> students) {
            school.setStudents(students);
            return this;
        }

        public Builder withPrograms(List<Program> programs) {
            school.setPrograms(programs);
            return this;
        }

        public Builder withSubjects(List<Subject> subjects) {
            school.setSubjects(subjects);
            return this;
        }

        public School build() {
            if (school.getClasses() == null) {
                school.setClasses(new ArrayList<Class>());
            }
            if (school.getTeachers() == null) {
                school.setTeachers(new ArrayList<Teacher>());
            }
            if (school.getStudents() == null) {
                school.setStudents(new ArrayList<Student>());
            }
            if (school.getPrograms() == null) {
                school.setPrograms(new ArrayList<Program>());
            }
            if (school.getSubjects() == null) {
                school.setSubjects(new ArrayList<Subject>());
            }
            return school;
        }
    }

}
