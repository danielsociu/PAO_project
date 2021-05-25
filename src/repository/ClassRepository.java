package repository;

import config.DatabaseConnection;
import models.*;
import java.sql.*;

public class ClassRepository {
    public void addClass(School school, models.Class myClass) {
        String sql = "insert into class (id_class, year, year_period, letter, id_program, id_school) " +
                "values (null, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, myClass.getYear());
            statement.setString(2, myClass.getYearPeriod());
            statement.setString(3, myClass.getLetter());
            statement.setInt(4, myClass.getProgram().getIdProgram());
            statement.setInt(5, school.getIdSchool());
            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idClass = generatedKey.getInt(1);
                    myClass.setIdClass(idClass);
                } else {
                    throw new SQLException("Failed to get class id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeClass(models.Class myClass) {
        String sql = "delete from class where id_class = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClass(models.Class myClass) {
        String sql = "update class " +
                "set year = ?, year_period = ?, letter = ?, id_program = ? " +
                "where id_class = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, myClass.getYear());
            statement.setString(2, myClass.getYearPeriod());
            statement.setString(3, myClass.getLetter());
            statement.setInt(4, myClass.getProgram().getIdProgram());
            statement.setInt(5, myClass.getIdClass());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubject(models.Class myClass, Subject subject, Teacher teacher) {
        String sql = "insert into class_subjects (id_class, id_subject, pid_teacher) " +
                "values (?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            statement.setInt(2, subject.getIdSubject());
            statement.setString(3, teacher.getPid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeSubject(models.Class myClass, Subject subject) {
        String sql = "delete from class_subjects where id_class = ? and id_subject = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, myClass.getIdClass());
            statement.setInt(2, subject.getIdSubject());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
