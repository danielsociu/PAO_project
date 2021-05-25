package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    public void addStudent(Student student) {
        String sql = "insert into student (pid_student, id_class, first_name, last_name, birth_date) " +
                "values (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
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

    public void removeStudent(Student student) {
        String sql = "delete from student where pid_student = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, student.getPid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudentClass(models.Class studentClass, Student student) {
        String sql = "update student set id_class = ? where pid_student = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            if (studentClass == null) {
                statement.setNull(1, 0);
            } else {
                statement.setInt(1, studentClass.getIdClass());
            }
            statement.setString(2, student.getPid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getStudents(School school) {
        String sql = "select s.* from student s, class c " +
                "where s.id_class = c.id_class and c.id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, school.getIdSchool());
            ResultSet rs = statement.executeQuery();
            ArrayList<Student> students = new ArrayList<>();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date"),
                        rs.getString("pid_student"),
                        null
                ));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
