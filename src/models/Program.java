package models;

import helpers.FileWritable;

import java.util.Objects;

public class Program implements FileWritable {
    private String name;
    private int numberYears;

    public Program(String name, int numberYears) {
        this.name = name;
        this.numberYears = numberYears;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNumberYears() {
        return numberYears;
    }
    public void setNumberYears(int numberYears) {
        this.numberYears = numberYears;
    }

    @Override
    public String getFileName() {
        return "programs";
    }

    @Override
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + name + ',' + String.valueOf(numberYears));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberYears);
    }
}
