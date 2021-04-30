package models;

import helpers.FileWritable;

import java.util.*;

public class Grade implements FileWritable {
    private double score;
    private Date date;
    private String evaluationMethod;
    private Student student;
    private Teacher teacher;
    private Subject subject;

    private Grade() {

    }

    // public Grade(double score, Date date, String evaluationMethod, Student student, Teacher teacher, Subject subject) {
    //     this.score = score;
    //     this.date = date;
    //     this.evaluationMethod = evaluationMethod;
    //     this.student = student;
    //     this.teacher = teacher;
    //     this.subject = subject;
    // }

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
    public String toCSVString() {
        return (String.valueOf(hashCode()) + ',' + score + "," +  date.getTime()
                + ',' + evaluationMethod + ',' + teacher.hashCode() + ',' + student.hashCode()
                + ',' + subject.hashCode()
        );
    }

    @Override
    public String getFileName() {
        return "grades";
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, student, date);
    }

    @Override
    public String toString() {
        return "Grade " + score + " at " + subject + " by "
                + teacher + " to " + student;
    }

    public static class Builder {
        private Grade grade = new Grade();

        public Grade.Builder withScore(double score) {
            grade.setScore(score);
            return this;
        }
        public Grade.Builder withDate(Date date) {
            grade.setDate(date);
            return this;
        }
        public Grade.Builder withEvaluationMethod(String evaluationMethod) {
            grade.setEvaluationMethod(evaluationMethod);
            return this;
        }
        public Grade.Builder withStudent(Student student) {
            grade.setStudent(student);
            return this;
        }
        public Grade.Builder withTeacher(Teacher teacher) {
            grade.setTeacher(teacher);
            return this;
        }
        public Grade.Builder withSubject(Subject subject) {
            grade.setSubject(subject);
            return this;
        }
        public Grade build() {
            return this.grade;
        }
    }
}
