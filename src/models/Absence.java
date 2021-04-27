package models;

import java.util.Date;

public class Absence {
    Student student;
    Subject subject;
    Teacher teacher;
    Date date;
    boolean motivated;

    private Absence() {

    }

    // public Absence(Student student, Subject subject, Date date, Teacher teacher) {
    //     this.student = student;
    //     this.subject = subject;
    //     this.date = date;
    //     this.motivated = false;
    //     this.teacher = teacher;
    // }

    // public Absence(Student student, Subject subject, Date date, Teacher teacher, boolean motivated) {
    //     this.student = student;
    //     this.subject = subject;
    //     this.date = date;
    //     this.teacher = teacher;
    //     this.motivated = motivated;
    // }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isMotivated() {
        return motivated;
    }

    public void setMotivated(boolean motivated) {
        this.motivated = motivated;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public static class Builder {
        private Absence absence = new Absence();

        public Absence.Builder withDate(Date date) {
            absence.setDate(date);
            return this;
        }
        public Absence.Builder withStudent(Student student) {
            absence.setStudent(student);
            return this;
        }
        public Absence.Builder withTeacher(Teacher teacher) {
            absence.setTeacher(teacher);
            return this;
        }
        public Absence.Builder withSubject(Subject subject) {
            absence.setSubject(subject);
            return this;
        }
        public Absence.Builder withMotivated(boolean motivated) {
            absence.setMotivated(motivated);
            return this;
        }
        public Absence build() {
            return this.absence;
        }
    }
}
