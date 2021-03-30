package Models;

import java.util.Date;

public class Grade {
    double score;
    Date date;
    String evaluationMethod;

    public Grade(double score, Date date, String evaluationMethod) {
        this.score = score;
        this.date = date;
        this.evaluationMethod = evaluationMethod;
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
}
