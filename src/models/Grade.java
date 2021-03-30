package models;

import java.util.Date;

public class Grade {
    double score;
    Date date;
    String evaluationMethod;
    Student student;
    Teacher teacher;
    Subject subject;

    public Grade(double score, Date date, String evaluationMethod, Student student, Teacher teacher, Subject subject) {
        this.score = score;
        this.date = date;
        this.evaluationMethod = evaluationMethod;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvaluationMethod() {
        return evaluationMethod;
    }

    public void setEvaluationMethod(String evaluationMethod) {
        this.evaluationMethod = evaluationMethod;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Grade " + score + " at " + subject + " by "
                + teacher + " to " + student;
    }
}
