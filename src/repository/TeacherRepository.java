package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;

public class TeacherRepository {

    public void addTeacher(Teacher teacher) {
        String sql = "insert into teacher (pid_teacher, first_name, last_name, birth_date) " +
                "values (?, ?, ?, ?)";
        try (
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
        ) {
            statement.setString(1, teacher.getPid());
            statement.setString(2, teacher.getFirstName());
            statement.setString(3, teacher.getLastName());
            statement.setDate(4, new java.sql.Date(teacher.getBirthDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTeacherClasses(Teacher teacher) {
        String sqlQuery = "select * from teacher_class where pid_teacher = ?";
        String sqlAdd = "insert into teacher_class (pid_teacher, id_class) " +
                "values (?, ?)";
        try (
            PreparedStatement statementQuery = DatabaseConnection.getConnection().prepareStatement(
                sqlQuery,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            PreparedStatement statementAdd = DatabaseConnection.getConnection().prepareStatement(sqlAdd)
        ) {
            statementQuery.setString(1, teacher.getPid());
            ResultSet existentClasses = statementQuery.executeQuery();
            for (models.Class myClass: teacher.getClasses()) {
                boolean ok = true;
                while (existentClasses.next()) {
                    if (existentClasses.getInt("id_class") == myClass.getIdClass()) {
                        existentClasses.beforeFirst();
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    statementAdd.setString(1, teacher.getPid());
                    statementAdd.setInt(2, myClass.getIdClass());
                    statementAdd.addBatch();
                }
            }
            statementAdd.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
