package models;

import helpers.FileWritable;

import java.util.Date;
import java.util.Objects;

public class Person{
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String pid;

    public Person(String firstName, String lastName, Date birthDate, String pid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.pid = pid;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    public String toCSVString() {
        return (firstName + ',' + lastName + ',' +
                birthDate.getTime() + ',' + pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, pid);
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
