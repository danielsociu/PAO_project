package models;


import java.util.ArrayList;
import java.util.List;

public class School {
    private static School school = new School();
    private String name;
    private List<Class> classes;
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
        // public Builder withEmptyClasses() {
        //     school.setClasses(new ArrayList<Class>());
        //     return this;
        // }
        public School build() {
            if (school.getClasses() == null) {
                school.setClasses(new ArrayList<Class>());
            }
            return school;
        }
    }

}
