package models;


import java.util.ArrayList;
import java.util.List;

public class School {
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

    @Override
    public String toString() {
        return "School: " + name;
    }

    public static class Builder {
        private School school = new School();

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
            if (this.school.getClasses() == null) {
                this.school.setClasses(new ArrayList<Class>());
            }
            return this.school;
        }
    }

}
