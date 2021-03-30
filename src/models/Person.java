package models;

import java.util.Date;

public class Person {
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

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
