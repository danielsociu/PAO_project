package repository;

import config.DatabaseConnection;
import models.*;

import java.sql.*;

public class AbsenceRepository {

    public void addAbsence(School school, Absence absence) {
        String sqlAbsence = "insert into absence (id_absence, pid_student, pid_teacher, id_subject, id_class, date, motivated) " +
                "values (null, ?, ?, ?, ?, ?, ?)";
        try (
            PreparedStatement statementAbsence = DatabaseConnection.getConnection().prepareStatement(sqlAbsence);
        ) {
            statementAbsence.setString(1, absence.getStudent().getPid());
            statementAbsence.setString(2, absence.getTeacher().getPid());
            statementAbsence.setInt(3, absence.getSubject().getIdSubject());
            statementAbsence.setInt(4, school.getIdSchool());
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
}
