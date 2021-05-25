package models;

import helpers.FileWritable;

import java.util.Date;
import java.util.Objects;

public class Absence implements FileWritable {
    private int idAbsence;
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

    public int getIdAbsence() {
        return this.idAbsence;
    }

    public void setIdAbsence(int idAbsence) {
        this.idAbsence = idAbsence;
    }

    @Override
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + motivated + "," +  date.getTime()
                + ',' +  teacher.hashCode() + ',' + student.hashCode()
                + ',' + subject.hashCode()
        );
    }

    @Override
    public String getFileName() {
        return "absences";
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, student, date);
    }

    @Override
    public String toString() {
        return "Absence " + " at " + subject + " by "
                + teacher + " to " + student;
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
        public Absence.Builder withIdAbsence(int idAbsence) {
            absence.setIdAbsence(idAbsence);
            return this;
        }
        public Absence build() {
            return this.absence;
        }
    }
}
