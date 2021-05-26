package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository {

    public void addGrade(models.Class myClass, Grade grade) {
        String sqlGrade = "insert into grade (id_grade, pid_student, pid_teacher, id_subject, id_class, score, date, evaluation_method) " +
                "values (null,?, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement statementGrade = DatabaseConnection.getConnection().prepareStatement(
                sqlGrade,
                Statement.RETURN_GENERATED_KEYS
            )) {
            statementGrade.setString(1, grade.getStudent().getPid());
            statementGrade.setString(2, grade.getTeacher().getPid());
            statementGrade.setInt(3, grade.getSubject().getIdSubject());
            statementGrade.setInt(4, myClass.getIdClass());
            statementGrade.setDouble(5, grade.getScore());
            statementGrade.setDate(6, new java.sql.Date(grade.getDate().getTime()));
            statementGrade.setString(7, grade.getEvaluationMethod());
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

    public void updateGrade(Grade grade) {
        String sql = "update grade set score = ? where id_grade = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setDouble(1, grade.getScore());
            statement.setInt(2, grade.getIdGrade());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrade(Grade grade) {
        String sql = "delete from grade where id_grade = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, grade.getIdGrade());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Grade> getGrades(
            models.Class myClass,
            List<Student> students,
            List<Subject> subjects,
            List<Teacher> teachers
    ) {
        String sql = "select * from grade where id_class = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            List<Grade> grades = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String pidStudent = rs.getString("pid_student");
                String pidTeacher = rs.getString("pid_teacher");
                int idSubject = rs.getInt("id_subject");
                Student myStudent = null;
                Teacher myTeacher = null;
                Subject mySubject = null;
                for (Student student: students) {
                    if (pidStudent.equals(student.getPid())) {
                        myStudent = student;
                        break;
                    }
                }
                for (Teacher teacher: teachers) {
                    if (pidTeacher.equals(teacher.getPid())) {
                        myTeacher = teacher;
                        break;
                    }
                }
                for (Subject subject: subjects) {
                    if (idSubject == subject.getIdSubject()) {
                        mySubject = subject;
                        break;
                    }
                }
                grades.add(
                        new Grade.Builder()
                                .withIdGrade(rs.getInt("id_grade"))
                                .withStudent(myStudent)
                                .withSubject(mySubject)
                                .withTeacher(myTeacher)
                                .withScore(rs.getDouble("score"))
                                .withEvaluationMethod(rs.getString("evaluation_method"))
                                .withDate(rs.getDate("date"))
                                .build()
                );
            }
            return grades;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
