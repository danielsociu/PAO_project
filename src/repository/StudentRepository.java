package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    public void addStudent(School school,Student student) {
        String sql = "insert into student (pid_student, id_school, id_class, first_name, last_name, birth_date) " +
                "values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, student.getPid());
            statement.setInt(2, school.getIdSchool());
            if (student.getStudentClass() != null) {
                statement.setInt(3, student.getStudentClass().getIdClass());
            } else {
                statement.setNull(3, 0);
            }
            statement.setString(4, student.getFirstName());
            statement.setString(5, student.getLastName());
            statement.setDate(6, new java.sql.Date(student.getBirthDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStudent(School school, Student student) {
        String sql = "delete from student where pid_student = ? and id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, student.getPid());
            statement.setInt(2, school.getIdSchool());
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

    public List<Student> getStudents(School school, List<models.Class> classes) {
        String sql = "select * from student where id_school = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, school.getIdSchool());
            ResultSet rs = statement.executeQuery();
            ArrayList<Student> students = new ArrayList<>();
            while (rs.next()) {
                int idClass = rs.getInt("id_class");
                models.Class myClass = null;
                for (models.Class studClass: classes) {
                    if (studClass.getIdClass() == idClass) {
                        myClass = studClass;
                    }
                }
                students.add(new Student(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date"),
                        rs.getString("pid_student"),
                        myClass
                ));
                if (myClass != null) {
                    List<Student> studentsClass = myClass.getStudents();
                    studentsClass.add(students.get(students.size() - 1));
                    myClass.setStudents(studentsClass);
                }
                System.out.println(students.get(students.size() -1));
                System.out.println(myClass);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
