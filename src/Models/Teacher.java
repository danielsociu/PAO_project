package Models;

import Helpers.Pair;

import java.util.Date;
import java.util.List;

public class Teacher extends Person {
    private List<Pair<Class, Subject>> classes;

    public Teacher(String firstName, String lastName, Date birthDate, String pid) {
        super(firstName, lastName, birthDate, pid);
    }
}
