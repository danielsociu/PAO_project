package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeRepository {

    public void addGrade(School school, Grade grade) {
        String sqlGrade = "insert into grade (id_grade, pid_student, pid_teacher, id_subject, id_class, score, date, evaluation_method) " +
                "values (null,?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement statementGrade = DatabaseConnection.getConnection().prepareStatement(sqlGrade);
        ) {
            statementGrade.setString(1, grade.getStudent().getPid());
            statementGrade.setString(2, grade.getTeacher().getPid());
            statementGrade.setInt(3, grade.getSubject().getIdSubject());
            statementGrade.setInt(4, school.getIdSchool());
            statementGrade.setDouble(4, grade.getScore());
            statementGrade.setDate(5, new java.sql.Date(grade.getDate().getTime()));
            statementGrade.setString(6, grade.getEvaluationMethod());
            statementGrade.executeUpdate();

            try (ResultSet generatedKey = statementGrade.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idGrade = generatedKey.getInt(1);
                    grade.setIdGrade(idGrade);
                } else {
                    throw new SQLException("Failed to get absence id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
