package models;

import helpers.FileWritable;

import java.util.Objects;

public class Subject implements FileWritable {
    private int idSubject;
    private String name;
    private String domain; 

    public Subject(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public Subject(int idSubject, String name, String domain) {
        this.idSubject = idSubject;
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

    public int getIdSubject() {
        return this.idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    @Override
    public String getFileName() {
        return "subjects";
    }

    @Override
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + name + ',' + domain);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, domain);
    }
}
