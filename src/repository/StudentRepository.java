package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;

public class StudentRepository {
    public void addStudent(Student student) {
        String sql = "insert into student (pid_student, id_class, first_name, last_name, birth_date) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, student.getPid());
            if (student.getStudentClass() != null) {
                statement.setInt(2, student.getStudentClass().getIdClass());
            } else {
                statement.setNull(2, 0);
            }
            statement.setString(3, student.getFirstName());
            statement.setString(4, student.getLastName());
            statement.setDate(5, new java.sql.Date(student.getBirthDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addStudentClass(Student student, models.Class studentClass) {
        String sql = "update student set id_class = ? where pid_student = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, studentClass.getIdClass());
            statement.setString(2, student.getPid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
