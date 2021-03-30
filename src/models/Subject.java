package models;

public class Subject {
    private String name;
    private String domain; // change to class!!

    public Subject(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return name;
    }
}
