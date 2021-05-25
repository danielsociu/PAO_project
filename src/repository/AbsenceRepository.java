package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;

public class AbsenceRepository {

    public void addAbsence(models.Class myClass, Absence absence) {
        String sqlAbsence = "insert into absence (id_absence, pid_student, pid_teacher, id_subject, id_class, date, motivated) " +
                "values (null, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement statementAbsence = DatabaseConnection.getConnection().prepareStatement(
                sqlAbsence,
                Statement.RETURN_GENERATED_KEYS
            )) {
            statementAbsence.setString(1, absence.getStudent().getPid());
            statementAbsence.setString(2, absence.getTeacher().getPid());
            statementAbsence.setInt(3, absence.getSubject().getIdSubject());
            statementAbsence.setInt(4, myClass.getIdClass());
            statementAbsence.setDate(5, new java.sql.Date(absence.getDate().getTime()));
            statementAbsence.setInt(6, absence.isMotivated() ? 1 : 0);
            statementAbsence.executeUpdate();

            try (ResultSet generatedKey = statementAbsence.getGeneratedKeys()) {
                if (generatedKey.next()) {
                    int idAbsence = generatedKey.getInt(1);
                    absence.setIdAbsence(idAbsence);
                } else {
                    throw new SQLException("Failed to get absence id!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAbsence(Absence absence) {
        String sql = "update absence set motivated = ? where id_absence = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, absence.isMotivated() ? 1 : 0);
            statement.setInt(2, absence.getIdAbsence());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAbsence(Absence absence) {
        String sql = "delete from absence where id_absence = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, absence.getIdAbsence());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
